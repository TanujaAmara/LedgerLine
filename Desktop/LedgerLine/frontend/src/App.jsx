
import { useEffect, useState } from "react";
import "./App.css";

const API = "http://localhost:8080/api";

function App() {
  const [accounts, setAccounts] = useState([]);
  const [transactions, setTransactions] = useState([]);
  const [name, setName] = useState("");
  const [accountId, setAccountId] = useState("");
  const [amount, setAmount] = useState("");
  const [message, setMessage] = useState("");
  const [loading, setLoading] = useState(false);

  async function loadAccounts() {
    try {
      const response = await fetch(`${API}/accounts`);
      if (!response.ok) throw new Error("Unable to load accounts");
      setAccounts(await response.json());
    } catch (error) {
      setMessage(error.message);
    }
  }

  async function loadTransactions() {
    try {
      const response = await fetch(`${API}/ledger/transactions`);
      if (!response.ok) throw new Error("Unable to load transactions");
      setTransactions(await response.json());
    } catch (error) {
      setMessage(error.message);
    }
  }

  useEffect(() => {
    loadAccounts();
    loadTransactions();
  }, []);

  async function createAccount(e) {
    e.preventDefault();
    setLoading(true);
    setMessage("");

    try {
      const response = await fetch(`${API}/accounts`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ accountHolderName: name })
      });

      const data = await response.json();
      if (!response.ok) throw new Error(data.message || "Account creation failed");

      setMessage(`Account created! Account Number: ${data.accountNumber}`);
      setName("");
      await loadAccounts();
    } catch (error) {
      setMessage(error.message);
    } finally {
      setLoading(false);
    }
  }

  async function makeTransaction(type, e) {
    e.preventDefault();
    setLoading(true);
    setMessage("");

    try {
      const endpoint = type === "DEPOSIT" ? "deposit" : "withdraw";

      const response = await fetch(`${API}/ledger/${endpoint}`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          accountId: Number(accountId),
          amount: Number(amount)
        })
      });

      const data = await response.json();

      if (!response.ok) {
        throw new Error(data.message || "Transaction failed");
      }

      setMessage(
        `${type} successful! Reference: ${data.transactionReference}`
      );

      setAmount("");
      await loadAccounts();
      await loadTransactions();
    } catch (error) {
      setMessage(error.message);
    } finally {
      setLoading(false);
    }
  }

  return (
    <div className="app">
      <header className="topbar">
        <div>
          <h1>LedgerLine</h1>
          <p>Secure Money Movement. Verifiable Ledger.</p>
        </div>
        <span className="secure">● Secure Banking</span>
      </header>

      <main className="container">
        <section className="welcome">
          <div>
            <p className="eyebrow">DIGITAL BANKING PLATFORM</p>
            <h2>Welcome to LedgerLine</h2>
            <p>Manage accounts and perform secure money transactions.</p>
          </div>
          <div className="welcome-icon">₹</div>
        </section>

        {message && <div className="message">{message}</div>}

        <section className="stats">
          <div className="stat-card">
            <span>Total Accounts</span>
            <h2>{accounts.length}</h2>
          </div>
          <div className="stat-card">
            <span>Total Transactions</span>
            <h2>{transactions.length}</h2>
          </div>
          <div className="stat-card">
            <span>Security</span>
            <h2>Active</h2>
          </div>
        </section>

        <div className="section-grid">
          <section className="panel">
            <h2>Create Account</h2>
            <p className="subtext">Open a new LedgerLine account.</p>
            <form onSubmit={createAccount}>
              <label>Account Holder Name</label>
              <input
                value={name}
                onChange={e => setName(e.target.value)}
                placeholder="Enter full name"
                minLength={3}
                required
              />
              <button disabled={loading}>Create Account</button>
            </form>
          </section>

          <section className="panel">
            <h2>Money Transfer</h2>
            <p className="subtext">Deposit or withdraw money securely.</p>

            <form onSubmit={e => makeTransaction("DEPOSIT", e)}>
              <label>Account ID</label>
              <input
                type="number"
                min="1"
                value={accountId}
                onChange={e => setAccountId(e.target.value)}
                placeholder="Enter account ID"
                required
              />

              <label>Amount (₹)</label>
              <input
                type="number"
                min="0.01"
                step="0.01"
                value={amount}
                onChange={e => setAmount(e.target.value)}
                placeholder="Enter amount"
                required
              />

              <div className="button-row">
                <button disabled={loading}>Deposit</button>
                <button
                  type="button"
                  className="danger"
                  disabled={loading}
                  onClick={e => makeTransaction("WITHDRAW", e)}
                >
                  Withdraw
                </button>
              </div>
            </form>
          </section>
        </div>

        <section className="panel accounts-panel">
          <div className="panel-heading">
            <div>
              <h2>Account Overview</h2>
              <p className="subtext">Your registered bank accounts.</p>
            </div>
            <button className="secondary" onClick={loadAccounts}>
              Refresh
            </button>
          </div>

          {accounts.length === 0 ? (
            <p>No accounts available.</p>
          ) : (
            accounts.map(account => (
              <div className="account-row" key={account.id}>
                <div>
                  <strong>{account.accountHolderName}</strong>
                  <p>{account.accountNumber} · Account ID: {account.id}</p>
                  <span className="active">{account.status}</span>
                </div>
                <div className="balance">
                  <small>Available Balance</small>
                  <strong>₹{account.balance}</strong>
                </div>
              </div>
            ))
          )}
        </section>

        <section className="panel accounts-panel">
          <div className="panel-heading">
            <div>
              <h2>Transaction History</h2>
              <p className="subtext">Recorded money movements.</p>
            </div>
            <button className="secondary" onClick={loadTransactions}>
              Refresh
            </button>
          </div>

          {transactions.length === 0 ? (
            <p>No transactions available.</p>
          ) : (
            [...transactions].reverse().map(transaction => (
              <div className="account-row" key={transaction.id}>
                <div>
                  <strong>{transaction.transactionType}</strong>
                  <p>{transaction.transactionReference}</p>
                  <small>{transaction.createdAt}</small>
                </div>
                <div className="balance">
                  <strong>₹{transaction.amount}</strong>
                  <small>{transaction.status}</small>
                </div>
              </div>
            ))
          )}
        </section>
      </main>

      <footer>LedgerLine © 2026 | Secure Digital Banking</footer>
    </div>
  );
}

export default App;
