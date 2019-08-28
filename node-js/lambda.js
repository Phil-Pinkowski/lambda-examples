const fetch = require("node-fetch");

const handler = async ({ body }) => {
  const { textUrl } = JSON.parse(body);
  const response = await fetch(textUrl);
  const text = await response.text();

  const result = {
    wordCount: text.split(" ").length,
    longestWord: text
      .split(" ")
      .reduce(
        (longest, next) => (next.length > longest.length ? next : longest),
        ""
      )
  };

  return {
    statusCode: 200,
    body: JSON.stringify(result)
  };
};

module.exports = { handler };
