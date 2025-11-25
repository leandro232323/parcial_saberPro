const btnBuscar = document.getElementById("searchButton")

function iniciar() {
    btnBuscar.addEventListener('click', () => {
        console.log("Dio click")
    })
}

document.addEventListener("DOMContentLoaded", iniciar)