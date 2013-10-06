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
 * @param {Number} timestamp
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
    pc.gReceiver = new cast.receiver.Receiver(pc.chromecast.CAST_APP_NAME,
        [ pc.chromecast.CAST_PROTOCOL ],
        "",
        5);

    // new our message handler
    pc.gMessageHandler = new pc.message.MessageHandler();
    // setup the channel factory with our app
    pc.gMessageHandler.setupChannelFactory(pc.gReceiver);
    // now start our app!
    pc.gReceiver.start();
}
