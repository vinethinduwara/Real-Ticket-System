import React, { useState } from 'react';
import axios from 'axios';

const ConfigurationForm = ({ onConfigSave }) => {
  const [maxCapacity, setMaxCapacity] = useState('');
  const [totalTickets, setTotalTickets] = useState('');
  const [ticketReleaseRate, setTicketReleaseRate] = useState('');
  const [customerRetrievalRate, setCustomerRetrievalRate] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();

    const config = {
      maxTicketCapacity: parseInt(maxCapacity),
      totalTickets: parseInt(totalTickets),
      ticketReleaseRate: parseInt(ticketReleaseRate),
      customerRetrievalRate: parseInt(customerRetrievalRate),
    };

    try {
      const response = await axios.post('http://localhost:8080/api/config/save', config, {
        headers: { 'Content-Type': 'application/json' },
      });

      console.log('Configuration saved:', response.data);
      alert('Configuration saved successfully!'); // Show success message
      onConfigSave(config);
    } catch (err) {
      console.error('Failed to save configuration:', err.response ? err.response.data : err.message);
      alert('Failed to save configuration. Please try again.'); // Show error message
    }
  };

  const handleLoadClick = async () => {
    try {
      const response = await axios.get('http://localhost:8080/api/config/load');
      const config = response.data;

      setMaxCapacity(config.maxTicketCapacity);
      setTotalTickets(config.totalTickets);
      setTicketReleaseRate(config.ticketReleaseRate);
      setCustomerRetrievalRate(config.customerRetrievalRate);

      console.log('Loaded Configuration:', config);
      alert('Configuration loaded successfully!'); // Show success message
    } catch (err) {
      console.error('Failed to load configuration:', err.message);
      alert('Failed to load configuration.'); // Show error message
    }
  };

  return (
    <div style={styles.container}>
      <h2 style={styles.header}>Configuration Settings</h2>
      <form onSubmit={handleSubmit} style={styles.form}>
        <div style={styles.inputGroup}>
          <label htmlFor="maxCapacity" style={styles.label}>Max Ticket Capacity:</label>
          <input
            id="maxCapacity"
            type="number"
            placeholder="Enter max ticket capacity"
            value={maxCapacity}
            onChange={(e) => setMaxCapacity(e.target.value)}
            style={styles.input}
            required
          />
        </div>

        <div style={styles.inputGroup}>
          <label htmlFor="totalTickets" style={styles.label}>Total Tickets:</label>
          <input
            id="totalTickets"
            type="number"
            placeholder="Enter total tickets"
            value={totalTickets}
            onChange={(e) => setTotalTickets(e.target.value)}
            style={styles.input}
            required
          />
        </div>

        <div style={styles.inputGroup}>
          <label htmlFor="ticketReleaseRate" style={styles.label}>Ticket Release Rate (seconds):</label>
          <input
            id="ticketReleaseRate"
            type="number"
            placeholder="Enter ticket release rate"
            value={ticketReleaseRate}
            onChange={(e) => setTicketReleaseRate(e.target.value)}
            style={styles.input}
            required
          />
        </div>

        <div style={styles.inputGroup}>
          <label htmlFor="customerRetrievalRate" style={styles.label}>Customer Retrieval Rate (seconds):</label>
          <input
            id="customerRetrievalRate"
            type="number"
            placeholder="Enter customer retrieval rate"
            value={customerRetrievalRate}
            onChange={(e) => setCustomerRetrievalRate(e.target.value)}
            style={styles.input}
            required
          />
        </div>

        <button type="submit" style={styles.button}>Save Configuration</button>
        <button type="button" onClick={handleLoadClick} style={styles.loadButton}>Load Configuration</button>
      </form>
    </div>
  );
};

const styles = {
  container: {
    maxWidth: '500px',
    margin: '20px auto',
    padding: '20px',
    border: '1px solid #ccc',
    borderRadius: '8px',
    backgroundColor: '#f9f9f9',
    boxShadow: '0 2px 5px rgba(0, 0, 0, 0.1)',
  },
  header: {
    textAlign: 'center',
    color: '#333',
  },
  form: {
    display: 'flex',
    flexDirection: 'column',
    gap: '15px',
  },
  inputGroup: {
    display: 'flex',
    flexDirection: 'column',
  },
  label: {
    marginBottom: '5px',
    fontSize: '14px',
    color: '#555',
  },
  input: {
    padding: '10px',
    fontSize: '16px',
    border: '1px solid #ccc',
    borderRadius: '4px',
  },
  button: {
    padding: '10px',
    fontSize: '16px',
    color: '#fff',
    backgroundColor: '#007bff',
    border: 'none',
    borderRadius: '4px',
    cursor: 'pointer',
  },
  loadButton: {
    padding: '10px',
    fontSize: '16px',
    color: '#fff',
    backgroundColor: '#28a745',
    border: 'none',
    borderRadius: '4px',
    cursor: 'pointer',
    marginTop: '10px',
  },
};

export default ConfigurationForm;




