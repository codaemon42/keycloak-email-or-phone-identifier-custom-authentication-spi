<#import "template.ftl" as layout>
<@layout.registrationLayout displayInfo=false; section>
    <#if section = "form">
        <form action="${url.loginAction}" method="post">
            <div class="form-group">
                <label for="username">${labelText}</label>
                <input type="text" id="username" name="username" class="form-control" required />
            </div>
            <div class="form-group">
                <button type="submit" class="btn btn-primary">${buttonText}</button>
            </div>
        </form>
    </#if>
</@layout.registrationLayout>