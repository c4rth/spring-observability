import papaparse from './papaparse/papaparse.js';
import {SharedArray} from 'k6/data';
import http from 'k6/http';
import { check, sleep } from 'k6';
import { parseHTML } from 'k6/html';

export const options = {
    discardResponseBodies: false,
    scenarios: {
        users: {
            executor: 'constant-arrival-rate',
            exec: 'scenario_users',
            rate: 1,
            timeUnit: '1s',
            duration: '60s',
            preAllocatedVUs: 10,
        },
        admins: {
            executor: 'constant-arrival-rate',
            exec: 'scenario_admins',
            rate: 1,
            timeUnit: '1s',
            duration: '60s',
            preAllocatedVUs: 2,
        },
    },
};

const url = 'https://computer-database.gatling.io';
const requestParams = {
    'headers': {
        'accept': 'text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8',
        'accept-language': 'en-US,en;q=0.5',
        'accept-encoding': 'gzip, deflate',
        'user-agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10.15; rv:109.0) Gecko/20100101 Firefox/119.0'
    }
};

const csvData = new SharedArray('another data name', function () {
    return papaparse.parse(open('./data/search.csv'), {header: true}).data;
});

export function scenario_users() {
    search();
    browse();
}

export function scenario_admins() {
    search();
    browse();
    edit();
}

export default function() {
}

function search() {
    http.get(url + '/', requestParams);
    sleep(1);
    const randomData = csvData[Math.floor(Math.random() * csvData.length)];
    const res1 = http.get(url + `/computers?f=${randomData.searchCriterion}`, requestParams);
    const doc = parseHTML(res1.body);
    let firstMatch = null;
    doc.find('a').toArray().forEach((link) => {
        if (firstMatch == null && link.text().includes(randomData.searchComputerName)) {
            firstMatch = link.attr('href');
        }
    });
    sleep(1);
    const res2 = http.get(url + firstMatch);
    check(res2, {
        'status is 200': (r) => r.status === 200,
    });
    sleep(1);
}

function browse() {
    for (let i = 0; i < 4; i++) {
        http.get(url + `/computers?p=${i}`);
        sleep(1);
    }
}

function edit() {
    const payload = {
        name: 'Beautiful Computer',
        introduced: '2012-05-30',
        discontinued: '',
        company: '37,'
    };

    const params = {
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded', // Form data encoding type
        },
    };
    const res = http.post(url + '/computers/new', payload, params);
    check(res, {
        'status is 200': (r) => r.status === 200,
    });
}