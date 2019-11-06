import React, { Component } from "react";
import PropTypes from "prop-types";
import { searchForTrain } from "../../actions/trainActions";
import { connect } from "react-redux";
import { searchForCoach } from "../../actions/coachActions";
import Coach from "./Coach";

class BookingCoach extends Component {
  constructor() {
    super();
    this.state = {
      currentCoach: 1,
      stringError: ""
    };

    this.onReturnToTrainSearch = this.onReturnToTrainSearch.bind(this);
  }

  componentWillReceiveProps(nextProps) {
    if (nextProps.error) {
      this.setState({ stringError: nextProps.error.stringError });
    }
  }

  onCoachNumberClick = number => {
    number.preventDefault();
    const { trainParam } = this.props.search;
    const { directionParam } = this.props.search;

    this.setState({
      currentCoach: parseInt(number.target.value)
    });
    this.props.searchForCoach(
      trainParam.id,
      directionParam.departureDate,
      number.target.value
    );
  };

  createCoachNumbers = number => {
    let coachNumbers = [];
    for (let i = 0; i < number; i++) {
      if (i + 1 === this.state.currentCoach) {
        coachNumbers.push(
          <li key={"coachNumber#" + i + 1}>
            <span>{i + 1}</span>
          </li>
        );
      } else {
        coachNumbers.push(
          <li key={"coachNumber#" + i + 1}>
            <button
              value={i + 1}
              className="a-button"
              onClick={this.onCoachNumberClick.bind(this)}
            >
              {i + 1}
            </button>
          </li>
        );
      }
    }
    return coachNumbers;
  };

  onReturnToTrainSearch() {
    const { directionParam } = this.props.search;
    if (Object.values(directionParam).length >= 3)
      this.props.searchForTrain(
        directionParam.departureStation.value,
        directionParam.destinationStation.value,
        directionParam.departureDate
      );
    this.props.history.push("/booking/train");
  }

  render() {
    const { trainParam } = this.props.search;
    const { directionParam } = this.props.search;
    const { coach } = this.props.coach;
    const { stringError } = this.state;
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

                            <td>
                              {directionParam.departureDate}
                              {" - "}
                              {trainParam.departureTime}
                            </td>
                            <td>
                              {trainParam.arrivalDate}
                              {" - "}
                              {trainParam.arrivalTime}
                            </td>

                            <td>{trainParam.duration}</td>
                          </tr>
                        </tbody>
                      </table>
                    </div>
                    <button
                      type="button"
                      className="btn btn-primary-o f-right"
                      onClick={this.onReturnToTrainSearch}
                    >
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
                    {this.createCoachNumbers(trainParam.coachNumber)}
                  </ul>
                </div>
                <Coach
                  key={coach.number}
                  coach={coach}
                  stringError={stringError}
                  history={this.props.history}
                />
              </div>
            </div>
          </div>
        </div>
      </React.Fragment>
    );
  }
}

BookingCoach.propTypes = {
  searchForCoach: PropTypes.func.isRequired,
  searchForTrain: PropTypes.func.isRequired,
  coach: PropTypes.object.isRequired,
  search: PropTypes.object.isRequired,
  error: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
  coach: state.coach,
  search: state.search,
  error: state.error
});

export default connect(
  mapStateToProps,
  { searchForCoach, searchForTrain }
)(BookingCoach);
