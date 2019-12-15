import React, { Component } from "react";
import IcoMoon from "react-icomoon";
import { connect } from "react-redux";
import classnames from "classnames";
import { getStations } from "../../actions/stationActions";
import { searchForTrain } from "../../actions/trainActions";
import { currentDate } from "../../util/dateFunctions";
import { cleanErrors } from "../../actions/errorActions";
import { setDirectionSearchParam } from "../../actions/searchParamActions";
import PropTypes from "prop-types";
import Select from "react-select";

class LineSearchForm extends Component {
  constructor() {
    super();
    this.state = {
      departureDate: currentDate(),
      departureStation: { value: 1, label: "Zaporizhzhya 1" },
      destinationStation: { value: 2, label: "Kyiv-Pasazhyrsky" },
      mappedErrors: {}
    };
    this.onSubmit = this.onSubmit.bind(this);
    this.onSwitchButton = this.onSwitchButton.bind(this);
    this.onChange = this.onChange.bind(this);
  }

  onChange(e) {
    this.setState({ [e.target.name]: e.target.value });
  }

  componentDidMount() {
    this.props.getStations();
  }

  onSwitchButton() {
    var buffStation = this.state.departureStation;
    this.setState({
      departureStation: this.state.destinationStation,
      destinationStation: buffStation
    });
  }

  async componentWillReceiveProps(nextProps) {
    if (Object.values(nextProps.search.directionParam).length >= 3) {
      const {
        departureDate,
        departureStation,
        destinationStation
      } = nextProps.search.directionParam;
      this.setState({
        departureDate,
        departureStation,
        destinationStation
      });
    }
    if (nextProps.error) {
      this.setState({ mappedErrors: nextProps.error.mappedErrors });
    }
  }

  onSubmit(e) {
    e.preventDefault();
    const direction = {
      departureDate: this.state.departureDate,
      departureStation: this.state.departureStation,
      destinationStation: this.state.destinationStation
    };
    this.props.searchForTrain(
      direction.departureStation.value,
      direction.destinationStation.value,
      direction.departureDate
    );
    this.props.setDirectionSearchParam(direction);
  }

  handleDepartureChange = departureStation => {
    this.setState({ departureStation });
  };

  handleDestinationChange = destinationStation => {
    this.setState({ destinationStation });
  };

  render() {
    const { stations } = this.props.station;
    const { mappedErrors } = this.state;

    return (
      <div
        className="site-blocks-cover inner-page-cover overlay background-pic"
        data-stellar-background-ratio="0.5"
      >
        <div className="container">
          <div className="row align-items-center justify-content-center text-center">
            <div className="col index-booking-form">
              <div className="form-search-wrap p-2 overflowXAuto">
                <form className="minWidht900" onSubmit={this.onSubmit}>
                  <div className="row align-items-center">
                    <div className="p-3 no-sm-border border-right w-23">
                      <div className="select-wrap">
                        <span className="icon">
                          <IcoMoon icon="location" />
                        </span>
                        <Select
                          className={classnames("", {
                            "alert-select-border": mappedErrors.departureStation
                          })}
                          value={this.state.departureStation}
                          onChange={this.handleDepartureChange}
                          options={stations}
                        />
                        {mappedErrors.departureStation && (
                          <div
                            className="alert alert-danger alert-search"
                            role="alert"
                          >
                            {mappedErrors.departureStation}
                          </div>
                        )}
                      </div>
                    </div>

                    <div className="p-3 no-sm-border w-4">
                      <button
                        className="f-right p-color a-button float-none"
                        onClick={this.onSwitchButton}
                      >
                        <IcoMoon icon="tab" />
                      </button>
                    </div>

                    <div className="p-3 no-sm-border border-left border-right w-23">
                      <div className="select-wrap">
                        <span className="icon">
                          <IcoMoon icon="location" />
                        </span>
                        <Select
                          className={classnames("", {
                            "alert-select-border":
                              mappedErrors.destinationStation
                          })}
                          value={this.state.destinationStation}
                          onChange={this.handleDestinationChange}
                          options={stations}
                        />
                        {mappedErrors.destinationStation && (
                          <div
                            className="alert alert-danger alert-search"
                            role="alert"
                          >
                            {mappedErrors.destinationStation}
                          </div>
                        )}
                      </div>
                    </div>

                    <div className="w-25 p-3 no-sm-border border-right">
                      <div className="icon-wrap">
                        <span className="icon">
                          <IcoMoon icon="calendar" />
                        </span>
                        <input
                          type="date"
                          name="departureDate"
                          required="required"
                          className={classnames("form-control", {
                            "alert-select-border": mappedErrors.departureDate
                          })}
                          value={this.state.departureDate}
                          onChange={this.onChange}
                        />
                        {mappedErrors.departureDate && (
                          <div
                            className="alert alert-danger alert-search"
                            role="alert"
                          >
                            {mappedErrors.departureDate}
                          </div>
                        )}
                      </div>
                    </div>

                    <div className="w-25 p-3 no-sm-border">
                      <input
                        type="submit"
                        className="btn btn-primary-o"
                        value="Search And Order"
                      />
                    </div>
                  </div>
                </form>
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

LineSearchForm.propTypes = {
  station: PropTypes.object.isRequired,
  search: PropTypes.object.isRequired,
  error: PropTypes.object.isRequired,
  getStations: PropTypes.func.isRequired,
  searchForTrain: PropTypes.func.isRequired,
  setDirectionSearchParam: PropTypes.func.isRequired,
  cleanErrors: PropTypes.func.isRequired
};

const mapStateToProps = state => ({
  station: state.station,
  search: state.search,
  error: state.error
});

export default connect(mapStateToProps, {
  getStations,
  searchForTrain,
  setDirectionSearchParam,
  cleanErrors
})(LineSearchForm);
