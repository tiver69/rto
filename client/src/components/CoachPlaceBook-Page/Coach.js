import React, { Component } from "react";

class Coach extends Component {
  getRowByTypeId = typeId => {
    let rows = [];
    switch (typeId) {
      case 1:
        rows.push(2);
        rows.push(4);
        break;
      case 2:
        rows.push(3);
        rows.push(5);
        break;
      case 3:
        rows.push(2);
        rows.push(2);
        break;
      case 4:
        rows.push(2);
        rows.push(3);
        break;
      case 5:
        rows.push(1);
        rows.push(1);
        break;
      default:
        rows.push(0);
        rows.push(0);
    }
    return rows;
  };

  getPlaceView = (placeNumber, bookedPlaces) => {
    if (bookedPlaces.includes(placeNumber)) {
      return (
        <div
          className="col place disable"
          title="Booked"
          key={"place#" + placeNumber}
        >
          {placeNumber}
        </div>
      );
    } else {
      return (
        <button
          className="col place a-button"
          title="Free"
          value={placeNumber}
          key={"place#" + placeNumber}
        >
          {placeNumber}
        </button>
      );
    }
  };

  createCoachView = (number, typeId, bookedPlaces) => {
    let coach = [];
    let rows = this.getRowByTypeId(typeId);

    let placePerRow = number / rows[1];
    for (let i = 0; i < rows[0]; i++) {
      let coachRow = [];
      for (let j = 0; j < placePerRow; j++) {
        coachRow.push(this.getPlaceView(j * rows[1] + i + 1, bookedPlaces));
      }
      coach.push(
        <div className="row" key={"row#" + i}>
          {coachRow}
        </div>
      );
    }

    coach.push(
      <div className="row" key="pass">
        <div className="col place pass" title="Pass" />
      </div>
    );

    for (let i = rows[0]; i < rows[1]; i++) {
      let coachRow = [];
      for (let j = 0; j < placePerRow; j++) {
        coachRow.push(this.getPlaceView(j * rows[1] + i + 1, bookedPlaces));
      }
      coach.push(
        <div className="row" key={"row#" + i}>
          {coachRow}
        </div>
      );
    }

    return coach;
  };

  render() {
    const { coach } = this.props;
    return (
      <React.Fragment>
        <div className="col-lg-4 mt-5 text-left">
          <li key={coach.number}>
            <span>
              {coach.name} {coach.availablePlaces}/{coach.totalPlaces}
            </span>
          </li>
        </div>
        <hr />
        {/* <!-- coache places section --> */}
        <div className="wagon-floors">
          <div className="floor floor-1">
            {this.createCoachView(
              coach.totalPlaces,
              coach.typeId,
              coach.bookedPlaces
            )}
          </div>
        </div>
      </React.Fragment>
    );
  }
}

export default Coach;
