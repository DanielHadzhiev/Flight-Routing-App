import React, { useState } from 'react';
import './App.css';

function App() {
  const [origin, setOrigin] = useState('');
  const [destination, setDestination] = useState('');
  const [maxFlights, setMaxFlights] = useState('1');
  const [routes, setRoutes] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [searchPerformed, setSearchPerformed] = useState(false);

  const handleSearch = async (e) => {
    e.preventDefault();

    setError(null);

    if (!origin || !destination) {
      setError('Please provide both origin and destination');
      return;
    }

    setLoading(true);
    setSearchPerformed(true);

    try {
      const payload = {
        origin: origin.toUpperCase(),
        destination: destination.toUpperCase(),
        maxFlights: parseInt(maxFlights) || 1,
      };

      const response = await fetch('http://localhost:8080/api/get_routes', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(payload),
      });

      if (response.ok) {
        const data = await response.json();
        setRoutes(data);
        setError(null);
      } else if (response.status === 404) {
        const data = await response.json();
        
        if (data.message === "No routes found") {
          setRoutes([]);
          setError(null);
        } else {
          setError('Failed to fetch routes. Please try again.');
        }
      } else if (response.status === 500) {
        setError('Sorry, we encountered a server issue. Please try again later.');
      } else if (response.status === 400) {
        setError('Invalid input. Please check your data and try again.');
      } else {
        setError('An unexpected error occurred. Please try again.');
      }

      setLoading(false);
    } catch (err) {
      setError('Failed to fetch routes. Please check your network connection and try again.');
      setLoading(false);
    }
  };

  return (
    <div className="App">
      <div className="background-gradient"></div>

      <header className="App-header">
        <div className="logo-container">
          <div className="logo">
            <span className="logo-icon">✈</span>
            <span className="logo-text">SkyRouter</span>
          </div>
          <p className="tagline">Find your perfect flight path</p>
        </div>
      </header>

      <main className="App-main">
        <div className="hero-section">
          <h1>Discover the Best Routes for Your Journey</h1>
          <p>Enter your origin and destination to find the most affordable flight paths</p>
        </div>

        <div className="search-container">
          <form onSubmit={handleSearch}>
            <div className="form-row">
              <div className="form-group">
                <label htmlFor="origin">Origin</label>
                <input
                  type="text"
                  id="origin"
                  value={origin}
                  onChange={(e) => setOrigin(e.target.value.toUpperCase())}
                  placeholder="3-letter code (e.g. SOF)"
                  maxLength="3"
                  required
                />
              </div>

              <div className="form-group">
                <label htmlFor="destination">Destination</label>
                <input
                  type="text"
                  id="destination"
                  value={destination}
                  onChange={(e) => setDestination(e.target.value.toUpperCase())}
                  placeholder="3-letter code (e.g. MLE)"
                  maxLength="3"
                  required
                />
              </div>

              <div className="form-group">
                <label htmlFor="maxFlights">Max Flights</label>
                <input
                  type="number"
                  id="maxFlights"
                  value={maxFlights}
                  onChange={(e) => setMaxFlights(e.target.value)}
                  placeholder="Optional"
                  min="1"
                />
              </div>
            </div>

            <button type="submit" className="search-button" disabled={loading}>
              {loading ? (
                <span className="loading-spinner"></span>
              ) : (
                <>
                  <span className="search-icon">🔍</span>
                  <span>Find Routes</span>
                </>
              )}
            </button>
          </form>
        </div>

        {error && <div className="error-message">{error}</div>}
        <div className="results-container">
          {loading ? (
            <div className="loading-container">
              <div className="loading-animation">
                <div className="plane-animation">✈</div>
                <div className="loading-text">Searching for the best routes...</div>
              </div>
            </div>
          ) : searchPerformed && routes.length > 0 ? (
            <>
              <h2 className="results-title">
                <span className="highlight">Available Routes</span>
                <span className="route-count">{routes.length} options found</span>
              </h2>

              <div className="routes-list">
                {routes.map((route, index) => (
                  <div key={index} className="route-card">
                    <div className="route-header">
                      <div className="price-tag">
                        <span className="currency">$</span>
                        <span className="route-price">{route.totalPrice}</span>
                      </div>
                      <div className="route-summary">
                        <div className="route-cities">
                          <span>{route.route[0]}</span>
                          <span className="route-arrow">→</span>
                          <span>{route.route[route.route.length - 1]}</span>
                        </div>
                        <div className="route-stops">
                          {route.route.length === 2 ? (
                            <span className="direct-badge">Direct Flight</span>
                          ) : (
                            <span className="stops-badge">{route.route.length - 2} stop{route.route.length - 2 !== 1 ? 's' : ''}</span>
                          )}
                        </div>
                      </div>
                    </div>

                    <div className="route-path">
                      {route.route.map((city, cityIndex) => (
                        <React.Fragment key={cityIndex}>
                          <div className="city-node">
                            <div className="city-code">{city}</div>
                            {cityIndex < route.route.length - 1 && (
                              <div className="flight-line">
                                <div className="flight-icon">✈</div>
                              </div>
                            )}
                          </div>
                        </React.Fragment>
                      ))}
                    </div>

                    <div className="route-details">
                      <div className="detail-item">
                        <span className="detail-icon">🛫</span>
                        <span>Total flights: {route.route.length - 1}</span>
                      </div>
                      <button className="select-route-btn">Select This Route</button>
                    </div>
                  </div>
                ))}
              </div>
            </>
          ) : searchPerformed && !error ? (
            <div className="no-routes">
              <div className="no-routes-icon">🔍</div>
              <h3>No routes found</h3>
              <p>We couldn't find any routes from {origin} to {destination}.</p>
              <p>Try different airports or check for spelling errors.</p>
            </div>
          ) : null}
        </div>
      </main>
    </div>
  );
}

export default App;
