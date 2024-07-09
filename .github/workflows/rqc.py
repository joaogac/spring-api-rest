import os
import requests
import json
import time
def main():
    # Replace the placeholders with your actual data
    CLIENT_ID = os.getenv("STK_AI_CLIENT_ID")
    CLIENT_KEY = os.getenv("STK_AI_CLIENT_SECRET")
    ACCOUNT_SLUG = os.getenv("STK_AI_CLIENT_REALM")
    QC_SLUG = os.getenv("QC_SLUG")
    INPUT_DATA = os.getenv("INPUT_DATA")
    # INPUT_DATA = """
    #     {
    #         "input_data": "public class DiscountCalculator { public double calculateDiscount(String customerType, double purchaseAmount) { double discount = 0.0; if (customerType.equals(\"Regular\")) { if (purchaseAmount > 1000) { discount = purchaseAmount * 0.05; } else { discount = purchaseAmount * 0.02; } } else if (customerType.equals(\"Premium\")) { if (purchaseAmount > 1000) { discount = purchaseAmount * 0.10; } else { discount = purchaseAmount * 0.07; } } else if (customerType.equals(\"VIP\")) { if (purchaseAmount > 1000) { discount = purchaseAmount * 0.15; } else { discount = purchaseAmount * 0.12; } } else { discount = 0.0; } return discount; } }"
    #     }
    # """

    # Execute the steps
    access_token = get_access_token(ACCOUNT_SLUG, CLIENT_ID, CLIENT_KEY)
    execution_id = create_rqc_execution(QC_SLUG, access_token, INPUT_DATA)

    execution_status = {
        "status": "OK",
        "result": "{ \"output_data\": \"Some random output\" }"
    }
    # execution_status = get_execution_status(execution_id, access_token)

    result = execution_status['result']

    # Remove the leading and trailing ```json and ``` for correct JSON parsing
    if result.startswith("```json"):
        result = result[7:-4].strip()

    result_data = json.loads(result)
    return json.dumps(result_data, indent=4)


def get_access_token(account_slug, client_id, client_key):
    url = f"https://idm.stackspot.com/{account_slug}/oidc/oauth/token"
    print(url);
    headers = {'Content-Type': 'application/x-www-form-urlencoded'}
    data = {
        'client_id': client_id,
        'grant_type': 'client_credentials',
        'client_secret': client_key
    }
    response = requests.post(url, headers=headers, data=data)
    response_data = response.json()
    print(response_data);
    return response_data['access_token']

def create_rqc_execution(qc_slug, access_token, input_data):
    url = f"https://genai-code-buddy-api.stackspot.com/v1/quick-commands/create-execution/{qc_slug}"
    headers = {
        'Content-Type': 'application/json',
        'Authorization': f'Bearer {access_token}'
    }
    data = {
        'input_data': input_data
    }
    response = requests.post(
        url,
        headers=headers,
        json=data
    )

    if response.status_code == 200:
        decoded_content = response.content.decode('utf-8')  # Decode bytes to string
        extracted_value = decoded_content.strip('"')  # Strip the surrounding quotes
        response_data = extracted_value
        print('ExecutionID:', response_data)
        return response_data
    else:
        print(response.status_code)
        print(response.content)


def get_execution_status(execution_id, access_token):
    url = f"https://genai-code-buddy-api.stackspot.com/v1/quick-commands/callback/{execution_id}"
    headers = {'Authorization': f'Bearer {access_token}'}
    i = 0
    time.sleep(3)
    while True:
        response = requests.get(
            url,
            headers=headers
        )
        response_data = response.json()
        status = response_data['progress']['status']
        if status in ['COMPLETED', 'FAILED']:
            return response_data
        else:
            print("Status:", f'{status} ({i})')
            print("Execution in progress, waiting...")
            i+=1
            time.sleep(5)  # Wait for 5 seconds before polling again


if __name__ == "__main__":
    result = main()
    print(result)