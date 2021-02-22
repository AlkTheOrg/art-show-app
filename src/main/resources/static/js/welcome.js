let homeBgImg = document.getElementById("home-bg-img");
let welcomeTxt = document.getElementById("welcome-txt");
let logo = document.getElementById("logo");


window.addEventListener("load", async function(){
    homeBgImg.style.opacity = "100";
    await sleep(500);
    // homeBgImg.style.filter = "blur(8px)";
    // homeBgImg.setAttribute('style', homeBgImg.getAttribute('style')
    //     + 'filter: blur(8px);-webkit-filter: blur(8px);');
    welcomeTxt.style.opacity = "100";
    // logo.style.opacity = "100";
});

function sleep(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
}