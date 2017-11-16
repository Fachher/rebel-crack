(Link to user manual: http://manuals.zeroturnaround.com/xrebel/)


Enabling XRebel for your application
====================================

Modify your server startup VM arguments to include -javaagent:/path/to/xrebel.jar.
To verify this, you should see the XRebel banner in your server log/console output 
similar to the following:

2014-04-07 22:36:13 XRebel: 
2014-04-07 22:36:13 XRebel: #############################################################
2014-04-07 22:36:13 XRebel: 
2014-04-07 22:36:13 XRebel:  XRebel 1.1.0
2014-04-07 22:36:13 XRebel:  (c) Copyright ZeroTurnaround AS, Estonia, Tartu.
2014-04-07 22:36:13 XRebel: 
2014-04-07 22:36:13 XRebel:  This product is licensed to ${name} (${organization}) 
...

After navigating to your application in the browser, you should see the following
message notifying you that the application started with XRebel:

2014-04-07 22:36:27 XRebel: Started XRebel for application: '${appName}'


Activating an XRebel license
============================

The first time you navigate to your application running with XRebel via the browser, 
you will be asked to follow the on-screen instructions to either register for a license, 
or to activate a license should you already have one sent to you.


Questions and Support
=====================

If you have any questions about XRebel or problems setting it up for your application,
please contact xrebel@zeroturnaround.com

