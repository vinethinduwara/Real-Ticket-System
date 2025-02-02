import React from "react";

const LogDisplay = ({ logs }) => {
  return (
    <div>
      
      <div
        
      >
        {logs.length > 0 ? (
          logs.map((log, index) => (
            <p key={index} style={{ margin: 0 }}>
              {log}
            </p>
          ))
        ) : (
          <p></p>
        )}
      </div>
    </div>
  );
};

export default LogDisplay
