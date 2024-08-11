import http from 'k6/http';
import { check } from 'k6';
import { parseHTML } from 'k6/html';

export default function () {
    // Send an HTTP GET request
    const res = http.get('https://example.com');

    // Check if the request was successful
    check(res, {
        'status is 200': (r) => r.status === 200,
    });

    // Parse the HTML response
    const doc = parseHTML(res.body);

    // Select all 'a' elements
    const links = doc.find('a');

    // Loop through the links and print the href attribute
    links.toArray().forEach((link) => {
        const url = link.attr('href');
        console.log(url);
    });
}