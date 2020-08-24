let variable = new XMLHttpRequest();

let answerBtn = document.getElementById("answer")

answerBtn.addEventListener("click", function () {
    console.log("Hello button clicked")
    variable.open("GET", "/api/quizzes", true,
        "simon.aust@hotmail.com", "password")
    variable.send()
    let json = '{"name":"John", "age":31, "city":"New York"}'
    let myJSONobj = JSON.parse(json)
    console.log(myJSONobj.city)
    document.getElementById("content").innerHTML =
        variable.responseText
    console.log("***")
    console.log(variable.responseText)
    console.log("***")
})