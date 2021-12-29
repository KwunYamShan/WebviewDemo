var mycustomjs = {};
mycustomjs.os = {};
mycustomjs.os.isIOS = /iOS|iPhone|iPad|iPod/i.test(navigator.userAgent);
mycustomjs.os.isAndroid = !mycustomjs.os.isIOS;
mycustomjs.callbacks = {}

//无回调
mycustomjs.takeNativeAction = function(commandname, parameters){
    console.log("myjs takenativeaction")
    var request = {};
    request.name = commandname;
    request.param = parameters;
    if(window.mycustomjs.os.isAndroid){
        console.log("android take native action" + JSON.stringify(request));
        window.mycustomwebview.takeNativeAction(JSON.stringify(request));
    } else {
        window.webkit.messageHandlers.mycustomwebview.postMessage(JSON.stringify(request))
    }
}

//有回调
mycustomjs.takeNativeActionWithCallback = function(commandname, parameters,callback){
    var callbackname = "nativetojs_callback_"+(new Date()).getTime() + "_"+Math.floor(Math.random() * 10000);//生成唯一参数   UUID更好
    mycustomjs.callbacks[callbackname] = callback
    var request = {};
    request.name = commandname;
    request.param = parameters;
    request.param.callbackname = callbackname
    if(window.mycustomjs.os.isAndroid){
        console.log("android take native action" + JSON.stringify(request));
        window.mycustomwebview.takeNativeAction(JSON.stringify(request));
    } else {
        window.webkit.messageHandlers.mycustomwebview.postMessage(JSON.stringify(request))
    }
}

mycustomjs.callback = function (callbackname, response) {
   var callbackobject = mycustomjs.callbacks[callbackname];
   if (callbackobject !== undefined){
       var ret = callbackobject(response);
       if(ret === false){
           return
       }
       delete mycustomjs.callbacks[callbackname];
   }
}
