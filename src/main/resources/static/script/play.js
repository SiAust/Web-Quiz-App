let variable = new XMLHttpRequest();

let button = document.getElementById("submit")

// button.addEventListener("click",function () {
//     variable.open("GET", "/api/quizzes", true,
//         "simon.aust@hotmail.com", "password")
//     variable.send()
//     let json = {name: "Simon",
//     title: "The Journey to Software Developer"}
//     let myJSONobj = JSON.parse(json.title)
//     console.log(myJSONobj.title)
//     document.getElementById("content").innerHTML =
//         variable.responseText
//     console.log("***")
//     console.log(variable.responseText)
//     console.log("***")
// });

let helloButton = document.getElementById("hello")

helloButton.addEventListener("click", function () {
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