document.getElementById("addSongButton").addEventListener("click", function() {

    document.getElementById("songForm").style.display = "block";
    document.getElementById("removeSongForm").style.display = "none";

});


document.getElementById("addSongForm").addEventListener("submit", function(event) {

    event.preventDefault();
    const album = document.getElementById("album").value;
    const title = document.getElementById("title").value;
    const year = document.getElementById("year").value;
    const tableBody = document.getElementById("songTable").getElementsByTagName('tbody')[0];
    addNewRowV1(tableBody ,album, title , year );
    document.getElementById("songForm").style.display = "none";
    document.getElementById("addSongForm").reset();

});

function addNewRowV1(tableBody ,Album, title, year){

    const newRowHTML = `
        <tr>
            <td>${Album}</td>
            <td>${title}</td>
            <td>${year}</td>
            <td><button class="btn btn-danger btn-sm deleteButton"><i class="fa-solid fa-trash"></i></button></td>
        </tr>
    `;
    tableBody.innerHTML += newRowHTML;

}



