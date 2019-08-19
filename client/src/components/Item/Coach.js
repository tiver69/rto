import React, { Component } from "react";

class Coach extends Component {
  createCoachView = number => {
    let coach = [];
    let placePerRow = number / 4;

    for (let i = 0; i < 4; i++) {
      let coachRow = [];
      for (let j = 0; j < placePerRow; j++) {
        coachRow.push(
          <div
            className="col place disable"
            title="booked"
            key={"place#" + i * placePerRow + j + 1}
          >
            {i * placePerRow + j + 1}
          </div>
        );
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
            {/* <div className="row">
        <div className="col place disable" title="Booked">
          i
        </div>

         <a className="col place" href="ticketDetail?${pageContext.request.getQueryString()}&selectedPlace=${i}&selectedCoach=${trainCoach.getTrainCoach().getId()}" title="<fmt:message key='booking.train.place.free'/>"><c:out value="${i}"/></a>
         </div> */}
            {this.createCoachView(coach.totalPlaces)}
            {/* <div className="row">
          <div className="col place pass" title="Pass" />
        </div> */}
          </div>
        </div>
      </React.Fragment>
    );
  }
}

export default Coach;
