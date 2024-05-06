function formatDateTime(dateTime) {
  try {
    const options = {
      day: "2-digit",
      month: "2-digit",
      year: "numeric",
      hour: "2-digit",
      minute: "2-digit",
      second: "2-digit",
    };

    const formattedDate = new Intl.DateTimeFormat("vi-VN", options).format(
      new Date(dateTime)
    );

    if (formattedDate !== "Invalid Date") {
      return formattedDate;
    } else {
      throw new Error("Invalid date value");
    }
  } catch (error) {
    console.error("Error formatting date:", error.message);
    return "Invalid Date";
  }
}

function addClickEventToRows() {
  const rows = document.querySelectorAll("#postList tr");

  rows.forEach((row) => {
    row.addEventListener("click", function () {
      // Loại bỏ lớp CSS "selected" khỏi tất cả các hàng
      rows.forEach((r) => r.classList.remove("selected"));

      // Thêm lớp CSS "selected" vào hàng được click
      this.classList.add("selected");
      selectedRowId = this.querySelector("td:first-child").textContent;
    });
  });
}

function openForm() {
  document.getElementById("popupForm").style.display = "block";
}

function closeForm() {
  document.getElementById("popupForm").style.display = "none";
}

function deleteData() {
  const selectedRow = document.querySelector("#postList tr.selected");

  if (selectedRow) {
    const selectedRowId =
      selectedRow.querySelector("td:first-child").textContent;
    const confirmDelete = confirm("Bạn có chắc muốn xóa bài đăng này?");

    if (confirmDelete) {
      fetch(`http://localhost:8081/api/homes/${selectedRowId}`, {
        method: "DELETE",
      })
        .then((response) => {
          if (response.ok) {
            alert("Xóa bài đăng thành công");
            fetchPosts();
            // Tải lại trang sau khi cập nhật thành công
            window.location.reload();
          } else {
            throw new Error("Network response was not ok");
          }
        })
        .catch((error) => console.error("Lỗi khi xóa bài đăng:", error))
        .finally(() => {
          selectedRow.classList.remove("selected");
        });
    }
  } else {
    alert("Vui lòng chọn một bài đăng để xóa");
  }
}

// Thêm sự kiện click vào button "Thêm"
document
  .querySelector("#crud-buttons button.add")
  .addEventListener("click", function () {
    document.body.classList.add("popup-open");
  });

// Thêm sự kiện click vào button "Đóng" trong popup
document
  .querySelector("#popupForm button:last-child")
  .addEventListener("click", function () {
    document.body.classList.remove("popup-open");
  });
function fetchPosts() {
  fetch("http://localhost:8081/api/homes/")
    .then((response) => response.json())
    .then((posts) => {
      const postList = document.getElementById("postList");
      postList.innerHTML = ""; // Xóa nội dung hiện tại của tbody

      posts.forEach((post) => {
        const row = document.createElement("tr");

        const idCell = document.createElement("td");
        idCell.textContent = post.id;

        const titleCell = document.createElement("td");
        titleCell.textContent = post.tieuDe;

        const contentCell = document.createElement("td");
        contentCell.textContent = post.noiDung;

        const dateCell = document.createElement("td");
        dateCell.textContent = formatDateTime(post.tgbd);

        const imgCell = document.createElement("td");
        const imageElement = document.createElement("img");
        imageElement.src = post.anh;
        imageElement.alt = "Ảnh";
        imageElement.style.height = "50px";
        imgCell.appendChild(imageElement);

        row.appendChild(idCell);
        row.appendChild(titleCell);
        row.appendChild(contentCell);
        row.appendChild(dateCell);
        row.appendChild(imgCell);

        postList.appendChild(row);
      });
      const postForm = document.getElementById("postForm");
      postForm.addEventListener("submit", async (post) => {
        post.preventDefault();

        const newTitle = document.getElementById("title").value;
        const newContent = document.getElementById("content").value;

        const newImgInput = document.getElementById("image");
        const newImg = "/img/" + newImgInput.files[0].name; // Get the File object
        console.log(newImg);
        if (newTitle !== "" && newContent !== "") {
          const data = {
            tieuDe: newTitle,
            noiDung: newContent,
            anh: newImg,
          };
          console.log(data);
          try {
            const response = await fetch("http://localhost:8081/api/homes/", {
              method: "POST",
              headers: {
                "Content-Type": "application/json",
              },
              body: JSON.stringify(data),
            });

            if (!response.ok) {
              throw new Error("Network response was not ok");
            }

            const newPost = await response.json();
            console.log(data);
            alert("Tạo mới bài đăng thành công");
            fetchPosts();
            // Đóng popup sau khi tạo người dùng thành công
            closeForm();
            // Tải lại trang sau khi cập nhật thành công
            window.location.reload();
          } catch (error) {
            console.error("Lỗi khi tạo mới bài đăng: " + error.message);
            alert("Lỗi khi tạo mới bài đăng");
          }
        } else {
          alert("Vui lòng điền đầy đủ thông tin");
        }
      });
      // Thêm sự kiện click vào các hàng
      addClickEventToRows();
    })
    .catch((error) => console.error("Lỗi khi tải dữ liệu người dùng:", error));
}

fetchPosts();
