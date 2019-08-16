import React, { Component } from "react";
import IcoMoon from "react-icomoon";
import { connect } from "react-redux";
import { getStations } from "../../actions/stationActions";
import { searchForTrain } from "../../actions/trainActions";
import PropTypes from "prop-types";
import Select from "react-select";

class LineSearchForm extends Component {
  constructor() {
    super();
    this.state = {
      departureDate: "",
      departureStation: 1,
      destinationStation: 2
    };
    this.onSubmit = this.onSubmit.bind(this);
    this.onChange = this.onChange.bind(this);
  }

  onChange(e) {
    this.setState({ [e.target.name]: e.target.value });
  }

  componentDidMount() {
    this.props.getStations();
  }

  onSubmit(e) {
    e.preventDefault();
    const direction = {
      departureDate: this.state.departureDate,
      departureStation: this.state.departureStation.value,
      destinationStation: this.state.destinationStation.value
    };
    console.log(direction);
    this.props.searchForTrain(
      direction.departureStation,
      direction.destinationStation,
      direction.departureDate
    );
  }

  handleDepartureChange = departureStation => {
    this.setState({ departureStation });
    // console.log(`Option1 selected:`, departureStation);
  };

  handleDestinationChange = destinationStation => {
    this.setState({ destinationStation });
    // console.log(`Option2 selected:`, destinationStation);
  };
  render() {
    const { stations } = this.props.station;
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
                          value={this.state.departureStation}
                          onChange={this.handleDepartureChange}
                          options={stations}
                        />
                      </div>
                    </div>

                    <div className="p-3 no-sm-border w-4">
                      <button className="f-right p-color a-button float-none">
                        <IcoMoon icon="tab" />
                      </button>
                    </div>

                    <div className="p-3 no-sm-border border-left border-right w-23">
                      <div className="select-wrap">
                        <span className="icon">
                          <IcoMoon icon="location" />
                        </span>
                        <Select
                          value={this.state.destinationStation}
                          onChange={this.handleDestinationChange}
                          options={stations}
                        />
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
                          className="form-control"
                          value={this.state.departureDate}
                          onChange={this.onChange}
                        />
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
  train: PropTypes.object.isRequired,
  getStations: PropTypes.func.isRequired,
  searchForTrain: PropTypes.func.isRequired
};

const mapStateToProps = state => ({
  station: state.station,
  train: state.train
});

export default connect(
  mapStateToProps,
  { getStations, searchForTrain }
)(LineSearchForm);
