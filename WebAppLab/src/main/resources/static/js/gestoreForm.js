
document.getElementById("clearButton").addEventListener("click", function(event) {

    event.preventDefault();
    document.querySelector("form").reset();

});

document.getElementById("sendButton").addEventListener("click", function(event) {

    event.preventDefault();
    const nome = document.getElementById("fname").value.trim();
    const cognome = document.getElementById("lname").value.trim();
    const email = document.getElementById("email").value.trim();
    const birthday = document.getElementById("birthday").value.trim();
    if (nome === "" || cognome === "" || email === "" || birthday === "") {

        alert("Per favore, completa tutti i campi obbligatori prima di inviare il form.");

    } else {

        alert("Il form è stato inviato con successo!");

    }

});