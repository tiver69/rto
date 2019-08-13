import React, { Component } from "react";
import Select from "react-select";

class SelectStations extends Component {
  state = {
    selectedOption: this.props.selectedOption
  };

  handleChange = selectedOption => {
    this.setState({ selectedOption });
    //   console.log(`Option selected:`, selectedOption);
  };

  render() {
    const { stations } = this.props;
    const { selectedOption } = this.state;

    return (
      <Select
        value={selectedOption}
        onChange={this.handleChange}
        options={stations}
      />
    );
  }
}

export default SelectStations;
