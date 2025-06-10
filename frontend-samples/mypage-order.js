function openModal(event) {
  event.stopPropagation();
  document.getElementById("modal").style.display = "flex";
}

function closeModal() {
  document.getElementById("modal").style.display = "none";
}

function openReviewModal(event) {
  event.stopPropagation();
  document.getElementById("review-modal").style.display = "flex";
}

function closeReviewModal() {
  document.getElementById("review-modal").style.display = "none";
}

window.onclick = function (e) {
  const modal = document.getElementById("modal");
  const reviewModal = document.getElementById("review-modal");

  if (e.target === modal) {
    modal.style.display = "none";
  }
  if (e.target === reviewModal) {
    reviewModal.style.display = "none";
  }
};

document.addEventListener("DOMContentLoaded", () => {
  const form = document.getElementById("review-form");
  form.addEventListener("submit", function (e) {
    e.preventDefault();
    const rating = form.rating.value;
    const content = form.content.value;
    console.log("별점:", rating);
    console.log("내용:", content);
    closeReviewModal();
    alert("리뷰가 작성되었습니다!");
  });
});
