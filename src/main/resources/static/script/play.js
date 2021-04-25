const TITLE = document.getElementById("title")
const QUESTION = document.getElementById("question")
const OPTIONS_FORM = document.getElementById("options")
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
    // Clear all previous children from OPTIONS_FORM
    removeAllChildren(OPTIONS_FORM)
    let answerType = quiz.isMultipleChoice ? "checkbox" : "radio"
    TITLE.innerHTML = quiz.title
    QUESTION.innerHTML = quiz.text
    let options = quiz.options
    for (let i = 0; i < options.length; i++) {
        let input = document.createElement("input")
        input.type = answerType
        input.id = "option" + i
        input.name = "answer"
        input.value = "option" + i

        let label = document.createElement("label")
        label.id = "label" + i
        label.htmlFor = "option" + i
        label.innerHTML = options[i].option

        OPTIONS_FORM.appendChild(input)
        OPTIONS_FORM.appendChild(label)
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
ANSWER_BTN.addEventListener("click", function () {
    console.log("Answer button clicked")
    // todo send request to API with answer
    postAnswerRequest.open("post", "/api/quizzes/" + quizzes[currentQuestion].id + "/solve"
        , true
        , "simon.aust@hotmail.com"
        , "password")
    postAnswerRequest.setRequestHeader("Content-type", "application/json;charset=UTF-8")
    // todo find quiz id and insert into url, add body
    postAnswerRequest.onload = function () {
        let jsonResponse = JSON.parse(this.response)
        console.log(this.response)
        // let jsonResponse = data.content
        if (jsonResponse.success) {
            // increment the score counter and glow green?
            score++
            USER_SCORE_SPAN.innerHTML = score.toString()
            for (let i = 0; i < USER_ANSWERS; i++) {
                document.getElementById("label" + USER_ANSWERS[i])
                    .style.backgroundColor = "lightgreen"
            }
        } else {
            for (let i = 0; i < jsonResponse.answers.length; i++) {
                document.getElementById("label" + (jsonResponse.answers[i] - 1))
                    .style.backgroundColor = "lightgreen"
            }
            let filteredArr = USER_ANSWERS.filter(value => !jsonResponse.answers.includes(value))
            console.log("filteredArr: " + filteredArr)
            for (let i = 0; i <filteredArr.length; i++) {
                document.getElementById("label" + (filteredArr[i] - 1))
                    .style.backgroundColor = "red"
            }
            console.log("USER_ANSWERS: " + USER_ANSWERS)
            // glow red
            USER_ANSWERS = []
        }
    }
    let json = []
    addUserAnswersToObject(json)
    postAnswerRequest.send(JSON.stringify(json))
    toggleAnsBTN(false)
    if (currentQuestion === quizzes.length - 1) {
        toggleNextQBTN(false)
        return
    }
    toggleNextQBTN(true)
})

const addUserAnswersToObject = (array) => {
    let children = OPTIONS_FORM.children
    for (let i = 0; i < children.length; i++) {
        let jsonObj = {
            "answer": null
        }
        if (children[i].nodeName === "INPUT") {
            if (children[i].checked) {
                let choice = parseInt((children[i].id.charAt(6))) + 1
                USER_ANSWERS.push(choice)
                jsonObj.answer = choice
                array.push(jsonObj)
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