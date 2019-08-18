import React, { Component } from "react";
import { connect } from "react-redux";
import { searchForCoaches } from "../../actions/coachActions";
import PropTypes from "prop-types";

class TrainTable extends Component {
  constructor() {
    super();
    this.state = {
      departureDate: ""
    };
  }

  onTrainSubmit = trainIdButton => {
    trainIdButton.preventDefault();
    this.props.searchForCoaches(
      trainIdButton.target.value,
      this.state.departureDate
    );
    this.props.history.push("/booking/coach");
  };

  async componentDidMount() {
    if (Object.values(this.props.search.trainParam).length >= 3) {
      this.state.departureDate = this.props.search.trainParam.departureDate;
    }
  }

  render() {
    const { train } = this.props;

    return (
      <tr className="row100">
        <td className="column100 column1" data-column="column1">
          <button
            className="btn btn-primary-o"
            onClick={this.onTrainSubmit.bind(train.id)}
            value={train.id}
          >
            {train.id}
          </button>
        </td>

        <td className="column100 column2" data-column="column2">
          {train.firstStationName} - {train.lastStationName}
        </td>

        <td className="column100 column4" data-column="column4">
          {train.departureTime}
        </td>
        <td className="column100 column5" data-column="column5">
          {train.arrivalTime}
        </td>

        <td className="column100 column6" data-column="column6">
          {train.duration}
        </td>
        <td className="column100 column7" data-column="column7">
          <table>
            <tbody>
              {train.coachTypeInfoList.map(coachType => (
                <tr key={coachType.coachTypeName}>
                  <td>
                    {coachType.coachTypeName} : {coachType.count}{" "}
                  </td>
                  <td>
                    {coachType.availablePlaces} / {coachType.totalPlaces}
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </td>
      </tr>
    );
  }
}

TrainTable.propTypes = {
  searchForCoaches: PropTypes.func.isRequired,
  search: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
  search: state.search
});

export default connect(
  mapStateToProps,
  { searchForCoaches }
)(TrainTable);
