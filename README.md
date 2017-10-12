# SigninGoogleSoussidev
Simple Signin with RxActivityResult
<br>
<a href='https://ko-fi.com/A243447K' target='_blank'><img height='36' style='border:0px;height:36px;'
src='https://az743702.vo.msecnd.net/cdn/kofi4.png?v=0' border='0' alt='Buy Me a Coffee at ko-fi.com' /></a>

[![Ansible Role](https://img.shields.io/badge/Developer-Soussidev-yellow.svg)]()
[![Hex.pm](https://img.shields.io/hexpm/l/plug.svg)]()
<br>

# Add dependencie to your project :

```gradle
dependencies {
    compile 'com.github.datalink747:Rx_java2_soussidev:1.3'
}
```

# Preview :

* Disconnect :</br>
<img src="picture/signin1.png" height="302" width="202">

* Connect :</br>
<img src="picture/signin2.png" height="302" width="202">
<br>

# Code in Java:

```java
* Function onRxActivityResult

 //function onRxActivityResult
    private void getResultActivity()
    {

        RxActivityResultCompact.startActivityForResult(this, signInIntent, RC_SIGN_IN)
                .subscribe(new Consumer<ActivityResult>() {
                    @Override
                    public void accept(@NonNull ActivityResult result) throws Exception {
                        if (result.isOk()) {

                            GoogleSignInResult res = Auth.GoogleSignInApi.getSignInResultFromIntent(result.getData());
                            handleSignInResult(res);
                        }
                    }
                });
    }

```
* setResult()
```java
 setResult(RC_SIGN_IN , signInIntent);
```
# SDK Required
+ Target sdk:<br>
[![API](https://img.shields.io/badge/API-23%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=23)
+ Min sdk:<br>
[![API](https://img.shields.io/badge/API-19%2B-orange.svg?style=flat)](https://android-arsenal.com/api?level=19)

# Social Media
<table style="border:0px;">
   <tr>
      <td>
<a href="https://www.linkedin.com/in/soussimohamed/">
<img src="picture/linkedin.png" height="100" width="100" alt="Soussi Mohamed">
</a>
      </td>
      <td>
         <a href="https://twitter.com/soussimohamed7/">
<img src="picture/Twitter.png" height="60" width="60" alt="Soussi Mohamed">
</a>
     </td>
  </tr> 
</table>  
# Licence

```
Copyright 2017 Soussidev, Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
