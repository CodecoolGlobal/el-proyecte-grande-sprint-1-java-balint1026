package com.codecool.puzzleshowdown.utils;

public  class EloCalculator {
    private static final int K = 40;

    private static double calculateExpectedScore(double player, double opponent) {
        return 1.0 / (1 + Math.pow(10, (opponent - player) / 400));
    }

    private static double calculateNewRating(double rating, double expectedScore, double actualScore) {
        return rating + K * (actualScore - expectedScore);
    }

    public static double[] calculate(double[] initialRatings) {

        double[] newRatings = new double[initialRatings.length];
        for (int i = 0; i < initialRatings.length; i++) {
            double playerRating = initialRatings[i];
            double expectedScore;
            double actualScore;

            for (int j = 0; j < initialRatings.length; j++) {
                if (j == i) continue;
                actualScore = i < j ? 1 : 0;
                double opponentRating = initialRatings[j];
                expectedScore = calculateExpectedScore(playerRating, opponentRating);
                playerRating = (calculateNewRating(playerRating, expectedScore, actualScore));
                newRatings[i] = playerRating;
            }
        }
        return normalize(newRatings, initialRatings);
    }

    private static double[] normalize(double[] newRatings, double[] initialRatings) {
        double[] normalizedRatings = new double[newRatings.length];
        double maxDiff = Math.abs(newRatings[0] - initialRatings[0]);
        for (int i = 1; i < newRatings.length; i++) {
            double diff = Math.abs(newRatings[i] - initialRatings[i]);
            if (diff > maxDiff) {
                maxDiff = diff;
            }
        }
        if (maxDiff < K) return newRatings;

        double scalingFactor = K / maxDiff;
        for (int i = 0; i < newRatings.length; i++){
            normalizedRatings[i] = Math.floor(initialRatings[i] + ((newRatings[i] - initialRatings[i]) * scalingFactor));
        }
        return normalizedRatings;
    }

}



