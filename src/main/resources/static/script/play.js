const TITLE = document.getElementById("title")
const QUESTION = document.getElementById("question")
const OPTIONS_CONT = document.getElementById("options")
const CURRENT_Q_SPAN = document.getElementById("current-question")
const USER_SCORE_SPAN = document.getElementById("user-score")

// Buttons
const ANSWER_BTN = document.getElementById("check-answer")
const NEXT_Q_BTN = document.getElementById("next-question")

let getQuizzesRequest = new XMLHttpRequest()
let postAnswerRequest = new XMLHttpRequest()
let quizzes
let USER_ANSWERS = []

let currentQuestion
let score = 0

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
    toggleNextQBTN(false)
    updateView(quizzes[0])
}
getQuizzesRequest.send()

function updateView(quiz) {
    // Clear all previous children from OPTIONS_CONT
    removeAllChildren(OPTIONS_CONT)
    let answerType = quiz.isMultipleChoice ? "checkbox" : "radio"
    TITLE.innerHTML = quiz.title
    QUESTION.innerHTML = quiz.text
    let options = quiz.options
    for (let i = 0; i < options.length; i++) {
        let div = document.createElement("div")
        div.className = "option"

        let input = document.createElement("input")
        input.type = answerType
        input.id = "option" + i
        input.name = "answer"
        input.value = "option" + i

        let label = document.createElement("label")
        label.id = "label" + i
        label.htmlFor = "option" + i
        label.innerHTML = options[i].option

        div.appendChild(input)
        div.appendChild(label)

        OPTIONS_CONT.appendChild(div)
    }
}

NEXT_Q_BTN.addEventListener("click", function () {
    console.log("Next question button clicked")
    // Check if we're at the last question. If true, disable next question button
    // and show feedback to user by greying that button out
    let current
    if (currentQuestion + 1 < quizzes.length) {
        current = ++currentQuestion
    } else {
        toggleNextQBTN(false)
    }
    CURRENT_Q_SPAN.innerHTML = (current + 1).toString()
    updateView(quizzes[current])
    toggleAnsBTN(true)
    toggleNextQBTN(false)
})

const toggleNextQBTN = (toggle) => {
    if (toggle) { // turn "on" next button
        NEXT_Q_BTN.disabled = false
        NEXT_Q_BTN.style.backgroundColor = "aliceblue"
        NEXT_Q_BTN.style.border = "2px blue dotted"
    } else { // turn "off" next button
        NEXT_Q_BTN.disabled = true
        NEXT_Q_BTN.style.backgroundColor = "lightgrey"
        NEXT_Q_BTN.style.border = "2px grey dotted"
    }
};

// Check the given answer against the API
ANSWER_BTN.addEventListener("click", function () { // todo error if answer correct highlight
    console.log("Answer button clicked")
    postAnswerRequest.open("post", "/api/quizzes/" + quizzes[currentQuestion].id + "/solve"
        , true
        , "simon.aust@hotmail.com" // todo get logged in user
        , "password")
    postAnswerRequest.setRequestHeader("Content-type", "application/json;charset=UTF-8")
    postAnswerRequest.onload = function () {
        let jsonResponse = JSON.parse(this.response)
        console.log(this.response)
        // let jsonResponse = data.content
        if (jsonResponse.success) {
            // increment the score counter and glow green?
            score++
            USER_SCORE_SPAN.innerHTML = score.toString()
            highlightCorrectAnswers(jsonResponse.answers)
        } else {
            highlightCorrectAnswers(jsonResponse.answers)
            highlightIncorrectAnswers(jsonResponse.answers)
        }
        USER_ANSWERS = [] // answers need to be emptied after each question to prevent errors
    }
    let json = []
    addUserAnswersToObject(json)
    postAnswerRequest.send(JSON.stringify(json))
    toggleAnsBTN(false)
    if (currentQuestion === quizzes.length - 1) { // next button disabled for final question
        toggleNextQBTN(false)
        return
    }
    toggleNextQBTN(true)
})

const highlightCorrectAnswers = (array) => {
    for (let i = 0; i < array.length; i++) {
        document.getElementById("label" + (array[i] - 1))
            .style.backgroundColor = "lightgreen"
    }
}

// Use an negate intersection between the user answers and actual answers, this
// allows us to highlight the incorrect answers only in red, overlapping
// correct answers will still be in unchanged
const highlightIncorrectAnswers = (correctAnsArr) => {
    let filteredArr = USER_ANSWERS.filter(value => !correctAnsArr.includes(value))
    console.log("filteredArr: " + filteredArr)
    for (let i = 0; i <filteredArr.length; i++) {
        document.getElementById("label" + (filteredArr[i] - 1))
            .style.backgroundColor = "red"
    }
    console.log("USER_ANSWERS: " + USER_ANSWERS)
}

const addUserAnswersToObject = (array) => {
    let children = OPTIONS_CONT.children
    for (let i = 0; i < children.length; i++) {
        let jsonObj = {
            "answer": null
        }
        let grandChildren = children[i].children
        for (let i = 0; i < grandChildren.length; i++) {
            if (grandChildren[i].nodeName === "INPUT") {
                if (grandChildren[i].checked) {
                    let choice = parseInt((grandChildren[i].id.charAt(6))) + 1
                    USER_ANSWERS.push(choice)
                    jsonObj.answer = choice
                    array.push(jsonObj)
                }
            }
        }
    }
};

const toggleAnsBTN = (toggle) => {
    if (toggle) { // turn "on" next button
        ANSWER_BTN.disabled = false
        ANSWER_BTN.style.backgroundColor = "aliceblue"
        ANSWER_BTN.style.border = "2px blue dotted"
    } else { // turn "off" next button
        ANSWER_BTN.disabled = true
        ANSWER_BTN.style.backgroundColor = "lightgrey"
        ANSWER_BTN.style.border = "2px grey dotted"
    }
};

const removeAllChildren = (parent) => {
    while (parent.lastChild) {
        parent.removeChild(parent.lastChild)
    }
};