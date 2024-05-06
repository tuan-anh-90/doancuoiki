function formatDateTime(dateTime) {
  const options = {
    day: "2-digit",
    month: "2-digit",
    year: "numeric",
    hour: "2-digit",
    minute: "2-digit",
    second: "2-digit",
  };
  return new Intl.DateTimeFormat("vi-VN", options).format(new Date(dateTime));
}

function fetchUsers() {
  fetch("http://localhost:8081/api/event/all")
    .then((response) => response.json())
    .then((events) => {
      const calendarList = document.getElementById("calendarList");
      calendarList.innerHTML = ""; // Clear the current content of the table

      events.forEach((event) => {
        const row = document.createElement("tr");

        // Create table cells for event data
        const userIdCell = document.createElement("td");
        userIdCell.textContent = event.userId;
        const eventCell = document.createElement("td");
        eventCell.textContent = event.text;
        const startDateCell = document.createElement("td");
        startDateCell.textContent = formatDateTime(event.startTime); // Format the start time
        const endDateCell = document.createElement("td");
        endDateCell.textContent = formatDateTime(event.endTime); // Format the end time

        row.appendChild(userIdCell);
        row.appendChild(eventCell);
        row.appendChild(startDateCell);
        row.appendChild(endDateCell);

        calendarList.appendChild(row);
      });
    })
    .catch((error) => console.error("Error loading calendar data:", error));
}

// Call the fetchUsers function to display the data when the page loads
fetchUsers();
