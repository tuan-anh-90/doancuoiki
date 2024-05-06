// Function to generate a random light color
function getRandomLightColor() {
  const letters = "0123456789ABCDEF";
  let color = "#";
  for (let i = 0; i < 3; i++) {
    const value = Math.floor(Math.random() * (128 - 255) + 255);
    const hex = value.toString(16);
    color += hex;
  }
  return color;
}
document.addEventListener("DOMContentLoaded", function () {
  fetchAllNotes();
});

function fetchAllNotes() {
  fetch("/api/notes/")
    .then((response) => {
      if (!response.ok) {
        throw new Error(`HTTP error! Status: ${response.status}`);
      }
      return response.json();
    })
    .then((notes) => {
      displayNotes(notes);
    })
    .catch((error) => {
      console.error("Fetch error:", error);
    });
}

function displayNotes(notes) {
  const noteContainer = document.getElementById("noteContainer");

  notes.forEach((note) => {
    const noteElement = document.createElement("div");
    noteElement.classList.add("note");

    const titleElement = document.createElement("h3");
    titleElement.textContent = note.tieuDe;
    noteElement.style.backgroundColor = getRandomLightColor();
    const contentElement = document.createElement("p");
    contentElement.textContent = note.noiDung;
    console.log(note);
    noteElement.appendChild(titleElement);
    noteElement.appendChild(contentElement);

    noteContainer.appendChild(noteElement);
  });
}
