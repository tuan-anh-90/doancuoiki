function toggleContent() {
  var contentNote = document.querySelector(".content_note");
  contentNote.classList.toggle("hidden");
}

// Function to fetch and display the note list in a table
function fetchNotes() {
  fetch("http://localhost:8081/api/notes/")
    .then((response) => response.json())
    .then((notes) => {
      // Assuming you have a button with id="saveButton" and form with id="noteForm"
      const saveButton = document.getElementById("saveButton");
      const noteForm = document.getElementById("noteForm");

      saveButton.addEventListener("click", async () => {
        const newTitle = document.getElementById("title").value;
        const newContent = document.getElementById("content").value;
        if (newTitle !== "" && newContent !== "") {
          const data = {
            tieuDe: newTitle,
            noiDung: newContent,
          };

          try {
            const response = await fetch("http://localhost:8081/api/notes/", {
              method: "POST",
              headers: {
                "Content-Type": "application/json",
              },
              body: JSON.stringify(data),
            });
            console.log(response);
            if (!response.ok) {
              throw new Error("Network response was not ok");
            }
            const newNote = await response.json();
            alert("Tạo mới note thành công");
            window.location.reload();
            fetchNotes();
          } catch (error) {
            console.error("Lỗi tạo note: " + error.message);
            alert("Lỗi tạo note");
          }
        } else {
          alert("Vui lòng điền đầy đủ thông tin");
        }
      });

      // Prevent the default form submission behavior
      noteForm.addEventListener("submit", (event) => {
        event.preventDefault();
      });
    })
    .catch((error) => {
      console.error("Lỗi: " + error.message);
    });
}

fetchNotes();
