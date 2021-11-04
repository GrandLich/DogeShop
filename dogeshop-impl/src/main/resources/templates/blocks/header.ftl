<#import "/spring.ftl" as spring/>

<!DOCTYPE HTML>
<html>
<head>
    <meta charset="UTF-8" />
    <title>${title}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
</head>

<body>

<nav class="navbar navbar-expand-lg navbar-light bg-light" id="mainNavbar">
    <div class="container-fluid">
        <a class="navbar-brand" href="#">
            <img height="100px" src="<@spring.url '/img/logo.png'/>" alt="">
        </a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
        </div>
        <#if authed>
                <div class="d-flex flex-column me-3">
                    ТУТ ИКОНКА КОРЗИНЫ
                </div>
            <#else>
                <div class="d-flex flex-column me-3">
                    <button class="btn btn-light border mb-1">SignIn</button>
                    <button class="btn btn-light border">SignUp</button>
                </div>
        </#if>
        <a href="">
            <img height="100px" src="<@spring.url '/img/profile.png'/>" alt="">
        </a>
    </div>
</nav>

<div class="container-lg" id="mainContainer">
    <div class="p-3">