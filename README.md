###Web Quiz App 

This is a Spring Boot API which will serve as a web quiz.

The client can create their own quizzes and send them to
the server.

The client can request a quiz by ID or all quizzes.

The client can solve the quiz by passing the ID into the
URL path `/api/quizzes/{id}/solve` and the answer 
as a query string parameter, `?answer=2`. A response will
be sent to the client giving feedback as to  the result
of their answer, correct or incorrect.