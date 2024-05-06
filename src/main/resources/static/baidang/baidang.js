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
document.addEventListener("DOMContentLoaded", function () {
  // Thực hiện yêu cầu GET đến API
  fetch("/api/homes/")
    .then((response) => response.json())
    .then((data) => displayPosts(data))
    .catch((error) => console.error("Error:", error));
});

function displayPosts(posts) {
  var postsContainer = document.getElementById("postsContainer");

  posts.forEach((post) => {
    var postContainer = document.createElement("div");
    postContainer.classList.add("container");

    // Thêm class "con-container" cho phần tử cha của mỗi bài đăng
    var conContainerDiv = document.createElement("div");
    conContainerDiv.classList.add("con-container");

    var bentraiDiv = document.createElement("div");
    bentraiDiv.classList.add("bentrai");

    const postImage = document.createElement("img");
    postImage.classList.add("post-image");
    postImage.src = post.anh;
    postImage.alt = "Hình ảnh mô tả";

    bentraiDiv.appendChild(postImage);
    conContainerDiv.appendChild(bentraiDiv);

    var benphaiDiv = document.createElement("div");
    benphaiDiv.classList.add("benphai");

    var tieudeH1 = document.createElement("h1");
    tieudeH1.classList.add("tieude");
    tieudeH1.textContent = post.tieuDe;

    var noidungP = document.createElement("p");
    noidungP.classList.add("noidung");
    noidungP.textContent = post.noiDung;

    var ngaydangP = document.createElement("p");
    ngaydangP.classList.add("ngaydang");
    ngaydangP.textContent = "Ngày đăng: " + formatDateTime(post.tgbd);

    benphaiDiv.appendChild(tieudeH1);
    benphaiDiv.appendChild(noidungP);
    benphaiDiv.appendChild(ngaydangP);

    conContainerDiv.appendChild(benphaiDiv);

    postContainer.appendChild(conContainerDiv);

    postsContainer.appendChild(postContainer);
  });
}
