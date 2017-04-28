<h1>ezChat Push Server</h1>

<h2>Information</h2>
This Server provides PUSH functionality for ezChat Android App and XMPP Protocol in gerneral.
<br>
It uses Google Firebase to provide the PUSH Service.
Only Device Tokens are delivered to google.

<h2>Howto</h2>
<h3>Requirements</h3>
* <b>Openfire</b>
    * Openfire XMPP Server
    * Database read Access
* <b>Google</b>
    * Firebase Account
    * Firebase Cloud Messaging AppID
* <b>XMPP Client</b>
    * ezChat Android App
    <br><i>Hint: you can use any XMPP app for messaging. But there won't be any PUSH support.</i>

<h3>Installation</h3>
* Setup XMPP Server
* Setup ezChatPush MySQL Database
* Allow Access to Openfire User Database
* Edit config.properties in Tomcat root folder

<h2>TODO</h2>
* normalize Database
* include Ressource support

<h2>See also</h2>
<a href="https://ezlife.eu/apps/gitlab/philippm/ezChat">ezChat Android App</a>