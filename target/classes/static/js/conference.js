
const addUser = () => {
  let text = document.getElementById("username").value
  document.getElementById("username").value = ""
  if(text !== ""){
    document.querySelector(".commitee").innerHTML +=
      `<div class="badge badge__user-exist">
        <input type="hidden" name="user" value="${text}">
      <div class="conf__user-container">
        <span class="conf__user">${text}</span>
        <button type="button" class="close" aria-label="Close"
        onclick = "removeElement(this)">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
    </div>`
  }
};

const addTheme = () => {
  let text = document.getElementById("theme").value
  document.getElementById("theme").value = ""
  if(text !== ""){
    document.querySelector(".themes").innerHTML +=
        `<div class="badge badge__user-exist">
        <input type="hidden" name="theme" value="${text}">
      <div class="conf__user-container">
        <span class="conf__user">${text}</span>
        <button type="button" class="close" aria-label="Close"
        onclick = "removeElement(this)">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
    </div>`
  }
};

const removeElement = (e) => {
  e.parentElement.parentElement.innerHTML = ""
}