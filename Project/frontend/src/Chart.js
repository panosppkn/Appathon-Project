import {Pie} from 'react-chartjs-2'
import React from 'react'
import ReactDOM from 'react-dom'
import axios from 'axios'
import qs from 'qs'
import {Button} from 'react-bootstrap'
import 'bootstrap/dist/css/bootstrap.min.css';

class Chart extends React.Component {
  constructor(props) {
    // highlight-range{3}
    super(props);
    this.handleClick = this.handleClick.bind(this);
    this.state = {
        chartData1: {},
        chartData2: {},
        chartData3: {},
        chartData4: {}
    }
  }

  handleClick = event => {
    // highlight-range{3}
    event.preventDefault();
    console.log(this.state)
    axios
         .get('http://localhost:8080/project/authorsByCountry')
        .then(response => {
            console.log(response)
            this.setState((state) => {
                return {chartData1: response.data}
            })
        })
        .catch(error => {
            console.log(error)
        })

    axios
             .get('http://localhost:8080/project/authorsBySettlement')
            .then(response => {
                console.log(response)
                this.setState((state) => {
                    return {chartData2: response.data}
                })
            })
            .catch(error => {
                console.log(error)
            })
    axios
             .get('http://localhost:8080/project/authorsByCategory')
            .then(response => {
                console.log(response)
                this.setState((state) => {
                    return {chartData3: response.data}
                })
            })
            .catch(error => {
                console.log(error)
            })

    axios
             .get('http://localhost:8080/project/articlesByWordCategory')
            .then(response => {
                console.log(response)
                this.setState((state) => {
                    return {chartData4: response.data}
                })
            })
            .catch(error => {
                console.log(error)
            })
  }

    render() {
        return (
            <div className="chart" >
                <Button onClick={this.handleClick} variant="secondary">Show Statistics</Button>
                <br />
                <br />
                <label>Authors By Country</label>
                <Pie
                    data={this.state.chartData1}
                    options={{}}
                />
                <br />
                <label>Authors By Settlement</label>
                <Pie
                    data={this.state.chartData2}
                    options={{}}
                />
                <br />
                <label>Articles Per Number of Authors</label>
                <Pie
                    data={this.state.chartData3}
                    options={{}}
                />
                <br />
                <label>Articles Per Number of Words</label>
                <Pie
                    data={this.state.chartData4}
                    options={{}}
                />
            </div>
        );
    }
}

ReactDOM.render(
  <Chart />,
  document.getElementById('root')
);

export default Chart;