/**
 * User: jchionh
 * Date: 8/28/13
 * Time: 12:09 AM
 */

function mainInit() {
    // new our state runner
    pc.gStateRunner = new pc.runstate.StateRunner();

    pc.gPrevTimestamp = 0;
    pc.gDelta = 0;

    pc.gCountAreaElement = document.getElementById('countArea');
    pc.gDeltaTimeAreaElement = document.getElementById('deltaTimeArea');
    pc.gPhotoAreaElement = document.getElementById('photoArea')


    var initialRunState = new pc.states.InitialState();
    pc.gStateRunner.addState(initialRunState);


    // call our mainloop the first time with a current timestamp
    mainLoop(Date.now());

}

/**
 * this is the mainloop, runs with timestamp
 * @param timestamp
 */
function mainLoop(timestamp) {

    // calculate our delta
    pc.gDelta = Math.max(0.0, timestamp - pc.gPrevTimestamp);
    pc.gPrevTimestamp = timestamp;
    pc.gDeltaTimeAreaElement.innerText = 'dt: ' + pc.gDelta + ' ms';

    // tell our window to call us back the next frames
    window.requestAnimFrame(mainLoop, pc.gPhotoAreaElement);

    // update state runner
    pc.gStateRunner.update(pc.gDelta);

    // states to render
    pc.gStateRunner.render(pc.gDelta, null);
    
}

/**
 * Init the reciever to start the communications channel
 */
function initReceiver() {
    var chromecastApp = new cast.receiver.Receiver(
        pc.chromecast.CAST_APP_NAME,
        [ pc.chromecast.CAST_NAMESPACE ],
        "",
        5);
    /*
     var remoteMedia = new cast.receiver.RemoteMedia();
     remoteMedia.addChannelFactory(receiver.createChannelFactory(pc.chromecast.CAST_NAMESPACE));
     */

    var messageHandler = new pc.message.MessageHandler();
    var channelHandler = new cast.receiver.ChannelHandler('PhotocastDebug');
    console.log('channelHandler debug: ' + channelHandler.getDebugString());
    /*
    channelHandler.addEventListener(cast.receiver.Channel.EventType.MESSAGE, messageHandler.onMessage);
    channelHandler.addEventListener(cast.receiver.Channel.EventType.OPEN, messageHandler.onChannelOpened);
    channelHandler.addEventListener(cast.receiver.Channel.EventType.CLOSED, messageHandler.onChannelClosed);
    */

    channelHandler.addEventListener(cast.receiver.Channel.EventType.MESSAGE, function(event) {
        var message = event.message;
        var channel = event.target;

        var messageArea = document.getElementById('messageArea');
        messageArea.innerHTML = JSON.stringify(message);

        console.log(JSON.stringify(message));
    });

    channelHandler.addEventListener(cast.receiver.Channel.EventType.OPEN, function(event) {
        var messageArea = document.getElementById('messageArea');
        messageArea.innerHTML = 'onChannelOpened';
        console.log('onChannelOpened');
    });

    channelHandler.addEventListener(cast.receiver.Channel.EventType.CLOSED, function(event) {
        var messageArea = document.getElementById('messageArea');
        messageArea.innerHTML = 'onChannelClosed';
        console.log('onChannelClosed');
    });


    /*
    this.mChannelHandler.addEventListener(
        cast.receiver.Channel.EventType.OPEN,
        this.onChannelOpened.bind(this));
    this.mChannelHandler.addEventListener(
        cast.receiver.Channel.EventType.CLOSED,
        this.onChannelClosed.bind(this));
        */

    chromecastApp.start();
}
