using Amazon.Lambda.APIGatewayEvents;
using Amazon.Lambda.Core;
using Newtonsoft.Json;
using System.Net.Http;
using System.Threading.Tasks;
using System.Linq;
using System.Net;

[assembly: LambdaSerializer(typeof(Amazon.Lambda.Serialization.Json.JsonSerializer))]
namespace Example
{
    public class Handler
    {
        public async Task<APIGatewayProxyResponse> Handle(APIGatewayProxyRequest request, ILambdaContext context)
        {
            var url = JsonConvert.DeserializeObject<RequestBody>(request?.Body).textUrl;

            var response = await new HttpClient().GetAsync(url);

            var text = await response.Content.ReadAsStringAsync();
            var wordCount = text.Split(" ").Length;
            var longestWord = text.Split(" ").Aggregate((longest, next) => next.Length > longest.Length ? next : longest);

            return new APIGatewayProxyResponse
            {
                StatusCode = (int)HttpStatusCode.OK,
                Body = JsonConvert.SerializeObject(new ResponseBody { wordCount = wordCount, longestWord = longestWord })
            };
        }
    }

    class ResponseBody
    {
        public int wordCount { get; set; }
        public string longestWord { get; set; }
    }

    class RequestBody
    {
        public string textUrl { get; set; }
    }
}
