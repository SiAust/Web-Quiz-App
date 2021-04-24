const TITLE = document.getElementById("title")
const QUESTION = document.getElementById("question")
const OPTIONS_FORM = document.getElementById("options")
const CURRENT_Q_SPAN = document.getElementById("current-question")

// Buttons
const ANSWER_BTN = document.getElementById("check-answer")
const NEXT_Q_BTN = document.getElementById("next-question")

let getQuizzesRequest = new XMLHttpRequest();
let postAnswerRequest = new XMLHttpRequest();
let quizzes;

let currentQuestion;

// Get quizzes from API
getQuizzesRequest.open("GET", "/api/quizzes", true,
    "simon.aust@hotmail.com", "password")
getQuizzesRequest.onload = function () {
    let data = JSON.parse(this.response)
    console.log(this.response)

    currentQuestion = 0
    quizzes = data.content
    let totalQSpans = document.getElementsByClassName("total-questions")
    for (let i = 0; i < totalQSpans.length; i++) {
        totalQSpans.item(i).innerHTML = quizzes.length
    }
    CURRENT_Q_SPAN.innerHTML = "1"
    updateView(quizzes[0])
}
getQuizzesRequest.send()

function updateView(quiz) {
    // Clear any previous children from OPTIONS_FORM
    removeAllChildren(OPTIONS_FORM)
    TITLE.innerHTML = quiz.title
    QUESTION.innerHTML = quiz.text
    let options = quiz.options
    for (let i = 0; i < options.length; i++) {
        let input = document.createElement("input")
        input.type = "checkbox"
        input.id = "option" + i
        input.name = "answer"
        input.value = "option" + i

        let label = document.createElement("label")
        label.htmlFor = "option" + i
        label.innerHTML = options[i].option

        OPTIONS_FORM.appendChild(input)
        OPTIONS_FORM.appendChild(label)
    }
}

// Check the given answer against the API
ANSWER_BTN.addEventListener("click", function () {
    console.log("Answer button clicked")
    // todo send request to API with answer
    postAnswerRequest.open("post", "/api/quizzes/" + quizzes[currentQuestion].id + "/solve", true,
        "simon.aust@hotmail.com", "password")
    // todo find quiz id and insert into url, add body
})

NEXT_Q_BTN.addEventListener("click", function () {
    console.log("Next question button clicked")
    // Check if we're at the last question. If true, disable next question button
    // and show feedback to user by greying that button out
    let current
    if (currentQuestion + 1 < quizzes.length) {
        current = ++currentQuestion
    } else {
        NEXT_Q_BTN.disabled = true
        NEXT_Q_BTN.style.backgroundColor = "lightgrey"
        NEXT_Q_BTN.style.border = "2px grey dotted"
        return
    }
    CURRENT_Q_SPAN.innerHTML = current + 1 // fixme
    updateView(quizzes[current])
})

const removeAllChildren = (parent) => {
    while (parent.lastChild) {
        parent.removeChild(parent.lastChild)
    }
};