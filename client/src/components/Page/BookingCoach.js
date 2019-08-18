import React, { Component } from "react";
import PropTypes from "prop-types";
import { connect } from "react-redux";

class BookingCoach extends Component {
  createCoachView = number => {
    let coach = [];
    let placePerRow = number / 4;

    for (let i = 0; i < 4; i++) {
      let coachRow = [];
      for (let j = 0; j < placePerRow; j++) {
        coachRow.push(
          <div className="col place disable" title="booked">
            {i * placePerRow + j + 1}
          </div>
        );
      }
      coach.push(<div className="row">{coachRow}</div>);
    }
    return coach;
  };

  render() {
    const { trainParam } = this.props.search;
    const { coaches } = this.props.coach;
    return (
      <React.Fragment>
        {/* header */}
        <div className="site-blocks-cover inner-page-cover overlay background-pic">
          <div className="container">
            <div className="row align-items-center justify-content-center text-center">
              <div className="col index-home-message table">
                <div className="row justify-content-center">
                  <div className="col text-center">
                    <div className="chosen-train-table-wraper">
                      <table data-vertable="ver4">
                        <thead>
                          <tr className="row100 head">
                            <th>You choose</th>
                            <th>Direction</th>
                            <th>Departure</th>
                            <th>Arrival</th>
                            <th>Duration</th>
                          </tr>
                        </thead>

                        <tbody>
                          <tr className="row100">
                            <td>#{trainParam.id}</td>
                            <td>
                              {trainParam.firstStationName} -
                              {trainParam.lastStationName}
                            </td>

                            <td>{trainParam.departureTime}</td>
                            <td>{trainParam.arrivalTime}</td>

                            <td>{trainParam.duration}</td>
                          </tr>
                        </tbody>
                      </table>
                    </div>
                    <button type="button" className="btn btn-primary-o f-right">
                      Return
                    </button>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        {/* main part */}
        <div className="container">
          <div className="row">
            <div className="col">
              <div className="row">
                {/* coaches list section */}
                <div className="col-lg-7 mt-5 text-center">
                  <ul className="custom-pagination">
                    {coaches.map(coach => (
                      <li key={coach.number}>
                        <span>{coach.number}</span>
                      </li>
                    ))}
                    <li>
                      <button className="a-button" href="#.html">
                        0
                      </button>
                    </li>
                  </ul>
                </div>
                <div className="col-lg-4 mt-5 text-left">
                  {coaches.map(coach => (
                    <li key={coach.number}>
                      <span>
                        {coach.name} {coach.availablePlaces}/{coach.totalPlaces}
                      </span>
                    </li>
                  ))}
                </div>

                <hr />
                {/* <!-- coache places section --> */}
                {coaches.map(coach => (
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
                ))}
              </div>
            </div>
          </div>
        </div>
      </React.Fragment>
    );
  }
}

BookingCoach.propTypes = {
  coach: PropTypes.object.isRequired,
  search: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
  coach: state.coach,
  search: state.search
});

export default connect(
  mapStateToProps,
  {}
)(BookingCoach);
