
document.getElementById("removeSongButton").addEventListener("click", function() {

    document.getElementById("removeSongForm").style.display = "block";
    document.getElementById("songForm").style.display = "none";

});

function removeSongNumber(event) {

    event.preventDefault();
    const number = parseInt(document.getElementById("songNumber").value);
    const rows =document.getElementById('songTable').querySelectorAll('tr');
    if (number > 0 && number <= rows.length) {

        rows[number ].remove();
        document.getElementById("removeSongForm").style.display = "none";
        document.getElementById("removeSongByNumber").reset();
        alert("Singolo rimosso con successo!");

    } else {

        alert("Numero singolo non valido!");

    }

}

document.querySelectorAll(".deleteButton").forEach(button => {

    button.addEventListener("click", function() {

        const row = button.closest("tr");
        row.parentNode.removeChild(row); // Elimina la riga

    });

});
