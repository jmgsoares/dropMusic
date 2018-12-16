function toggleDiv() {
    let x = document.getElementById("toggleableDiv");
    if (x.style.display === "none") {
        x.style.display = "block";
    } else {
        x.style.display = "none";
    }
}