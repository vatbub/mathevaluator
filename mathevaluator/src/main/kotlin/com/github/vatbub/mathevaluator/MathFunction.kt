package com.github.vatbub.mathevaluator

/**
 * Represents a function (like sine, cosine or sqrt).
 * A function may take no, one or more inputs and computes an output from it.
 * If the function does not take any input, consider extending [Constant] instead.
 *
 * @param minNumberOfArguments The minimum number of inputs that this function requires
 * @param maxNumberOfArguments The maximum number of inputs that this function can accept
 */
abstract class MathFunction(
    override val formulaRepresentation: String,
    private val minNumberOfArguments: Int,
    private val maxNumberOfArguments: Int
) : MathLiteral() {
    /**
     * Input arguments for the function
     */
    var params: List<MathExpression> = listOf()

    /**
     * Called by the evaluator. Checks the number of input arguments and then calls [.evaluateImpl].
     * Use [params] to specify the inputs.
     *
     * @return The result of [.evaluateImpl]
     */
    fun evaluate(): MathNumber {
        require(params.size >= minNumberOfArguments) {
            "Too few arguments for function ${this.formulaRepresentation}, number of supplied arguments: " +
                "${params.size}, minimum number of arguments: $minNumberOfArguments"
        }
        require(params.size <= maxNumberOfArguments) {
            "Too many arguments for function ${this.formulaRepresentation}, number of supplied arguments: " +
                "${params.size}, maximum number of arguments: $maxNumberOfArguments"
        }
        return evaluateImpl()
    }

    /**
     * Performs the actual computation. Use [.getParams] to get the inputs.
     *
     *
     * There is no need to check the number of input arguments as the number of inout arguments is already verified by [.evaluate]
     *
     *
     * Note: Do not call this method directly, call [.evaluate] instead as it performs additional checks on the inputs!
     *
     * @return The result of the function
     */
    abstract fun evaluateImpl(): MathNumber
    override fun supportsImplicitMultiplication(previousLiteral: MathLiteral): Boolean {
        return previousLiteral is MathNumber ||
            previousLiteral is Constant ||
            previousLiteral is RuntimeConstant ||
            previousLiteral is MathFunction ||
            previousLiteral is MathExpression
    }
}
