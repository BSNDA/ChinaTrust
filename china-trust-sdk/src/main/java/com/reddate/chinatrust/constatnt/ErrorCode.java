package com.reddate.chinatrust.constatnt;

/**
 * Define Error Code and the corresponding Error Message.
 *
 */
public enum ErrorCode {

    /**
     * The success.
     */
    SUCCESS(0, "success"),

    /**
     * other uncatched exceptions or error.
     */
    UNKNOW_ERROR(10000, "unknow error, please check the error log."),
    /**
     * transaction timeout.
     */
    TRANSACTION_TIMEOUT(10001, "the transaction is timeout."),

    /**
     * exception happens when transaction executes.
     */
    TRANSACTION_EXECUTE_ERROR(10002, "the transaction does not correctly executed."),

    /**
     * input parameter is illegal.
     */
    ILLEGAL_INPUT(10003, "input parameter is illegal."),

    /**
     * smart contract load failed.
     */
    LOAD_CONTRACT_FAILED(10004, "load contract failed."),

    /**
     * web3j load failed.
     */
    LOAD_WEB3J_FAILED(10005, "load web3j failed."),
    /**
     * ChinaTrust base exceptions or error.
     */
    BASE_ERROR(10006, "base exception error, please check the error log."),
    /**
     * public key is invalid.
     */
    PUBLICKEY_INVALID(10007, "the input public key is invalid."),

    /**
     * private key is invalid.
     */
    PRIVATEKEY_INVALID(10008, "the input private key is invalid, please check and input your private key."
    ),
    /**
     * deploy contract failed.
     */
    DEPLOY_CONTRACT_FAILED(10009, "deploy contract failed, please check the error log."),
    /**
     * call contract failed.
     */
    CALL_CONTRACT_FAILED(10010, "call contract failed, please check the error log."),
    /**
     * get block number error.
     */
    GET_BLOCK_NUMBER_ERROR(10011, "get block number error, please check the error log."),
    /**
     * get event callback error.
     */
    GET_EVENT_CALLBACK_ERROR(11001, "get event callback error, please check the error log."),
    /**
     * other uncatched exceptions or error.
     */
    GET_EVENT_CALLBACK_TIMEOUT_ERROR(11002, "get event callback error, please check the error log."),

    /**
     * ProofStore failed to issue
     */
    PROOFSTORE_ISSUE_FAILED(110101, "the hash failed to issue in the proofStore."
    ),

    /**
     * ProofStore failed to revoke
     */
    PROOFSTORE_REVOKE_FAILED(110102, "the hash failed to revoke in the proofStore."
    ),

    /**
     * ProofStore failed to renounceOwnership
     */
    PROOFSTORE_RENOUNCEOWNERSHIP_FAILED(110103, "the hash failed to renounceOwnership in the proofStore."
    ),
    /**
     * ProofStore failed to transferOwnership
     */
    PROOFSTORE_TRANSFEROWNERSHIP_FAILED(110104, "the hash failed to transferOwnership in the proofStore."
    ),

    /**
     * ChinaTrustERC721 failed to mint
     */
    CHINATRUSTERC721_MINT_FAILED(110201, "the token failed to mint in the chinaTrustERC721."
    ),

    /**
     * ChinaTrustERC721 failed to sendToNewChinaTrustFlow
     */
    CHINATRUSTERC721_SENDTONEWCHINATRUSTFLOW_FAILED(110201, "the token failed to sendToNewChinaTrustFlow in the chinaTrustERC721."
    ),

    /**
     * ChinaTrustERC721 failed to destroyToken
     */
    CHINATRUSTERC721_DESTROYTOKEN_FAILED(110201, "the token failed to destroyToken in the chinaTrustERC721."
    ),

    /**
     * ChinaTrustFlow failed to transferToNewFlow
     */
    CHINATRUSTFLOW_TRANSFERTONEWFLOW_FAILED(110301, "the token failed to transferToNewFlow in the chinaTrustFlow."
    ),

    /**
     * ChinaTrustFlow failed to changeHolder
     */
    CHINATRUSTFLOW_CHANGEHOLDER_FAILED(110302, "the token failed to changeHolder in the chinaTrustFlow."
    ),

    /**
     * ChinaTrustFlow failed to approveNewTransferTargets
     */
    CHINATRUSTFLOW_APPROVENEWTRANSFERTARGETS_FAILED(110303, "the token failed to approveNewTransferTargets in the chinaTrustFlow."
    ),

    /**
     * ChinaTrustFlow failed to transferTo
     */
    CHINATRUSTFLOW_TRANSFERTO_FAILED(110304, "the token failed to transferTo in the chinaTrustFlow."
    ),

    /**
     * Verify Document failed
     */
    CHINATRUST_VERIFY_FAILED(200100, "verify document is failed!");

    /**
     * error code.
     */
    private int code;

    /**
     * error message.
     */
    private String codeDesc;

    /**
     * Error Code Constructor.
     *
     * @param code The ErrorCode
     * @param codeDesc The ErrorCode Description
     */
    ErrorCode(int code, String codeDesc) {
        this.code = code;
        this.codeDesc = codeDesc;
    }

    /**
     * get ErrorType By errcode.
     *
     * @param errorCode the ErrorCode
     * @return errorCode
     */
    public static ErrorCode getTypeByErrorCode(int errorCode) {
        for (ErrorCode type : ErrorCode.values()) {
            if (type.getCode() == errorCode) {
                return type;
            }
        }
        return ErrorCode.UNKNOW_ERROR;
    }

    /**
     * Get the Error Code.
     *
     * @return the ErrorCode
     */
    public int getCode() {
        return code;
    }

    /**
     * Set the Error Code.
     *
     * @param code the new ErrorCode
     */
    protected void setCode(int code) {
        this.code = code;
    }

    /**
     * Gets the ErrorCode Description.
     *
     * @return the ErrorCode Description
     */
    public String getCodeDesc() {
        return codeDesc;
    }

    /**
     * Sets the ErrorCode Description.
     *
     * @param codeDesc the new ErrorCode Description
     */
    protected void setCodeDesc(String codeDesc) {
        this.codeDesc = codeDesc;
    }
}
