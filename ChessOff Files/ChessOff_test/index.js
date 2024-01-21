const baseUrl = "http://localhost:8000";

const findGameRoomEp = baseUrl + "/chess/find-game-room";

let socket = new SockJS(baseUrl + "/ws/play/chess");
let stompClient = Stomp.over(socket);
let playerAndRoomInfo;

findGameRoom();
async function findGameRoom() {
  try {
    const response = await fetch(findGameRoomEp);

    if (!response.ok) {
      alert(await response.text());
      return;
    }

    console.log(response);

    playerAndRoomInfo = await response.json();

    let roomNo = playerAndRoomInfo.roomNo;
    let playerIdentifier = playerAndRoomInfo.playerIdentifier;
    console.error(roomNo);

    stompClient.connect(
      {
        RoomNo: roomNo,
        PlayerIdentifier: playerIdentifier,
      },
      onConnected,
      onError
    );
  } catch (error) {
    alert(error);
  }
}

async function onConnected(payload) {
  let roomNo = playerAndRoomInfo.roomNo;
  let playerIdentifier = playerAndRoomInfo.playerIdentifier;

  stompClient.subscribe(`/gameroom/${roomNo}`, onMessageReceived, {
    // RoomNo: roomNo,
    PlayerIdentifier: playerIdentifier,
  });
  // (async () => {
  //   await stompClient.send(
  //     `/gameroom/${roomNo}`,
  //     ///${prompt("Enter room id to whom you want to send message: ")}
  //     {},
  //     JSON.stringify({ message: "Hello" })
  //   );
  // })();
}

function onError(error) {
  console.log(error);
}

function onMessageReceived(payload) {
  let message = JSON.parse(payload.body).message;
  alert(message);
}
