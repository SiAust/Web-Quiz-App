const TOPIC = document.getElementById('topic-type')
const SUBMITTED_BY = document.getElementById('user-name')
const QUESTION = document.getElementById("question")
const OPTIONS_CONT = document.getElementById("options")
const CURRENT_Q_SPAN = document.getElementById("current-question")
const USER_SCORE_SPAN = document.getElementById("user-score")

// Results
const RESULT_INFO = document.getElementById("result-info")
const RESULT_MSG = document.getElementById("result-msg")
const CORRECT_DIV = document.getElementById('correct')
const INCORRECT_DIV = document.getElementById('incorrect')
const SCOREBOARD_OUTER = document.getElementById('outer')

// Buttons
const ANSWER_BTN = document.getElementById("check-answer")
const NEXT_Q_BTN = document.getElementById("next-question")

// HTTP Requests
let getQuizzesRequest = new XMLHttpRequest()
let postAnswerRequest = new XMLHttpRequest()

let quizzes
let USER_ANSWERS = []

let currentQuestion
let score = 0

/* Gets the 'topic' query param from the URL */
let urlParams = new URLSearchParams(location.search)
let topic = urlParams.get('topic')

/* Get quizzes from API  */
getQuizzesRequest.open("GET", "/api/quizzes?topic=" + topic, true) /* TODO: enable pagination */
/* Execute this code when there's a response */
getQuizzesRequest.onload = function () {

    console.log('/play?topic=' + urlParams.get('topic'))

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

    // Hide result info
    RESULT_INFO.classList.value = "hidden"
    RESULT_MSG.style.display = "none"
    CORRECT_DIV.style.display = "none"
    INCORRECT_DIV.style.display = "none"

    // Remove animation from scoreboard svg so we can play it again
    SCOREBOARD_OUTER.style.animation = "none"

    TOPIC.innerHTML = quiz.topic
    SUBMITTED_BY.innerHTML = quiz.createdBy.userName

    let answerType = quiz.isMultipleChoice ? "checkbox" : "radio"
    // TITLE.innerHTML = quiz.title
    QUESTION.innerHTML = quiz.text
    let options = quiz.options
    /* Create n option elements according to options.length */
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
        NEXT_Q_BTN.style.backgroundColor = "cornflowerblue"
        NEXT_Q_BTN.style.border = "2px white solid"
        NEXT_Q_BTN.style.background = "linear-gradient(135deg, var(--left), var(--right)"
        NEXT_Q_BTN.style.cursor = "pointer"
    } else { // turn "off" next button
        NEXT_Q_BTN.disabled = true
        NEXT_Q_BTN.style.background = "lightgrey"
        NEXT_Q_BTN.style.border = "2px grey dotted"
        NEXT_Q_BTN.style.cursor = "initial"

    }
};

// Check the given answer against the API
ANSWER_BTN.addEventListener("click", function () { // todo error if answer correct highlight
    console.log("Answer button clicked")
    postAnswerRequest.open("post", "/api/quizzes/" + quizzes[currentQuestion].id + "/solve"
        , true)
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
            // show result message
            // RESULT_INFO.style.display = "flex"
            RESULT_INFO.classList.value = "correct-msg"
            // RESULT_MSG.textContent = "CORRECT, WELL DONE!"
            CORRECT_DIV.style.display = "block"
            SCOREBOARD_OUTER.style.animation = "drawStroke 1s linear 4s forwards, spin 4s, scale 2s"
            
            
        } else {
            // show result message
            // RESULT_INFO.style.display = "flex"
            RESULT_INFO.classList.value = "incorrect-msg"
            // RESULT_MSG.textContent = "INCORRECT!"
            INCORRECT_DIV.style.display = "block"

            highlightCorrectAnswers(jsonResponse.answers)
            highlightIncorrectAnswers(jsonResponse.answers)
        }
        RESULT_MSG.style.display = "flex"
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


const toggleAnsBTN = (toggle) => {
    if (toggle) { // turn "on" next button
        ANSWER_BTN.disabled = false
        ANSWER_BTN.style.backgroundColor = "cornflowerblue"
        ANSWER_BTN.style.border = "2px white solid"
        ANSWER_BTN.style.background = "linear-gradient(135deg, var(--left), var(--right)"
        ANSWER_BTN.style.cursor = "pointer"
    } else { // turn "off" next button
        ANSWER_BTN.disabled = true
        ANSWER_BTN.style.background = "lightgrey"
        ANSWER_BTN.style.border = "2px grey dotted"
        ANSWER_BTN.style.cursor = "initial"
    }
};

const highlightCorrectAnswers = (array) => {
    for (let i = 0; i < array.length; i++) {
      document.getElementById("label" + (array[i] -1))
            .parentElement.classList.add("answer-correct")
    }
}

// Use an negate intersection between the user answers and actual answers, this
// allows us to highlight the incorrect answers only in red, overlapping
// correct answers will still be in unchanged
const highlightIncorrectAnswers = (correctAnsArr) => {
    let filteredArr = USER_ANSWERS.filter(value => !correctAnsArr.includes(value))
    console.log("filteredArr: " + filteredArr)
    for (let i = 0; i <filteredArr.length; i++) {
        document.getElementById("label" + (filteredArr[i] -1))
            .parentElement.classList.add("answer-incorrect")
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

const removeAllChildren = (parent) => {
    while (parent.lastChild) {
        parent.removeChild(parent.lastChild)
    }
};