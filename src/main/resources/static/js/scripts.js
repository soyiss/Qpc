window.addEventListener('DOMContentLoaded', event => {

    // Navbar shrink function
    var navbarShrink = function () {
        const navbarCollapsible = document.body.querySelector('#mainNav');
        if (!navbarCollapsible) {
            return;
        }
        if (window.scrollY === 0) {
            navbarCollapsible.classList.remove('navbar-shrink')
        } else {
            navbarCollapsible.classList.add('navbar-shrink')
        }
    };

    // Shrink the navbar
    navbarShrink();

    // Shrink the navbar when page is scrolled
    document.addEventListener('scroll', navbarShrink);

    // Activate Bootstrap scrollspy on the main nav element
    const mainNav = document.body.querySelector('#mainNav');
    if (mainNav) {
        new bootstrap.ScrollSpy(document.body, {
            target: '#mainNav',
            offset: 72,
        });
    };

    // Collapse responsive navbar when toggler is visible
    const navbarToggler = document.body.querySelector('.navbar-toggler');
    const responsiveNavItems = [].slice.call(
        document.querySelectorAll('#navbarResponsive .nav-link')
    );
    responsiveNavItems.map(function (responsiveNavItem) {
        responsiveNavItem.addEventListener('click', () => {
            if (window.getComputedStyle(navbarToggler).display !== 'none') {
                navbarToggler.click();
            }
        });
    });
});

// contact controll
document.querySelector('#submitButton').addEventListener('click', function (event) {
    let name = document.querySelector('#name').value;
    let email = document.querySelector('#email').value;
    let phone = document.querySelector('#phone').value;
    let message = document.querySelector('#message').value;

    let params = {
        "name": name,
        "email": email,
        "phone": phone,
        "message": message
    };

    var xhr = new XMLHttpRequest();
    xhr.open('POST', '/contact');

    xhr.onreadystatechange = function () {	// xhr 객체가 바뀌었을 (요청이 오갔을때 동작)
        if (xhr.readyState == 4 && xhr.status == 200) {	// 모든 통신이 끝남(4), 정상일 때(200)
            console.log(xhr);
            if (xhr.responseText == "") {
                document.querySelector('#contact__result').innerHTML = "Thank you! I will reply to you soon.";	// 동작
            } else {
                let data = JSON.parse(xhr.responseText);
                const writeDateTime = new Date(data.wdate).toString();
                document.querySelector('#contact__result').innerHTML = "You already sent message when " + writeDateTime.substring(0,writeDateTime.indexOf('GMT')) + '<br>' + " try again?";
                document.querySelector('#contact__update__btn').style.display = 'block';
            }
        }
    }
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.send(JSON.stringify(params));	//요청 보내기
});


function contactUpdateYes() {
    let name = document.querySelector('#name').value;
    let email = document.querySelector('#email').value;
    let phone = document.querySelector('#phone').value;
    let message = document.querySelector('#message').value;

    let params = {
        "name": name,
        "email": email,
        "phone": phone,
        "message": message
    };
    var xhr = new XMLHttpRequest();
    xhr.open('POST', '/contact/insert');
    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4 && xhr.status == 200) {
            document.querySelector('#contact__result').innerHTML = "Thank you! I will reply to you soon.";	// 동작
            document.querySelector('#contact__update__btn').style.display = 'none';
        }
    }
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.send(JSON.stringify(params));	//요청 보내기
}

function contactUpdateNo() {
    document.querySelector('#contact__result').innerHTML = "";	// 동작
    document.querySelector('#contact__update__btn').style.display = 'none';
    document.querySelector('#submitButton').classList.remove('d-none');  // remove display none class
    document.querySelector('#name').disabled = false;
    document.querySelector('#email').disabled = false;
    document.querySelector('#phone').disabled = false;
    document.querySelector('#message').disabled = false;
}

$(function() {
    $('[data-toggle="tooltip"]').tooltip();
});