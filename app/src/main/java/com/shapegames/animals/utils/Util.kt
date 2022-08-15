package com.shapegames.animals.utils

object Util {

    const val LIKED: Boolean = true
    const val UNLIKED: Boolean = false
    const val MINIMUM_ID: Int = 0
    const val GRID_COUNT: Int = 2
    const val BREED_GRID_COUNT: Int = 2
    const val MAX_PERCENTAGE: Int = 100

    const val IMG_COUNT: String = "100"

    val dogsBreedsHashMap: HashMap<String, Array<String>> = hashMapOf(

        "affenpinscher" to emptyArray(),
        "african" to emptyArray(),
        "airedale" to emptyArray(),
        "akita" to emptyArray(),
        "appenzeller" to emptyArray(),
        "basenji" to emptyArray(),
        "beagle" to emptyArray(),
        "bluetick" to emptyArray(),
        "borzoi" to emptyArray(),
        "bouvier" to emptyArray(),
        "boxer" to emptyArray(),
        "brabancon" to emptyArray(),
        "briard" to emptyArray(),
        "chihuahua" to emptyArray(),
        "chow" to emptyArray(),
        "clumber" to emptyArray(),
        "cockapoo" to emptyArray(),
        "coonhound" to emptyArray(),
        "cotondetulear" to emptyArray(),
        "dachshund" to emptyArray(),
        "dalmatian" to emptyArray(),
        "dhole" to emptyArray(),
        "dingo" to emptyArray(),
        "doberman" to emptyArray(),
        "entlebucher" to emptyArray(),
        "eskimo" to emptyArray(),
        "germanshepherd" to emptyArray(),
        "groenendael" to emptyArray(),
        "havanese" to emptyArray(),
        "husky" to emptyArray(),
        "keeshond" to emptyArray(),
        "kelpie" to emptyArray(),
        "komondor" to emptyArray(),
        "kuvasz" to emptyArray(),
        "labradoodle" to emptyArray(),
        "labrador" to emptyArray(),
        "leonberg" to emptyArray(),
        "lhasa" to emptyArray(),
        "malamute" to emptyArray(),
        "malinois" to emptyArray(),
        "maltese" to emptyArray(),
        "mexicanhairless" to emptyArray(),
        "mix" to emptyArray(),
        "newfoundland" to emptyArray(),
        "otterhound" to emptyArray(),
        "papillon" to emptyArray(),
        "pekinese" to emptyArray(),
        "pembroke" to emptyArray(),
        "pitbull" to emptyArray(),
        "pomeranian" to emptyArray(),
        "pug" to emptyArray(),
        "puggle" to emptyArray(),
        "pyrenees" to emptyArray(),
        "redbone" to emptyArray(),
        "rottweiler" to emptyArray(),
        "saluki" to emptyArray(),
        "samoyed" to emptyArray(),
        "schipperke" to emptyArray(),
        "sharpei" to emptyArray(),
        "shiba" to emptyArray(),
        "shihtzu" to emptyArray(),
        "stbernard" to emptyArray(),
        "tervuren" to emptyArray(),
        "vizsla" to emptyArray(),
        "weimaraner" to emptyArray(),
        "whippet" to emptyArray(),
        "australian" to arrayOf(
            "shepherd"
        ),

        "buhund" to arrayOf(
            "norwegian"
        ),
        "bulldog" to arrayOf(
            "boston",
            "english",
            "french"
        ),
        "bullterrier" to arrayOf(
            "staffordshire"
        ),
        "cattledog" to arrayOf(
            "australian"
        ),

        "collie" to arrayOf(
            "border"
        ),

        "corgi" to arrayOf(
            "cardigan"
        ),

        "dane" to arrayOf(
            "great"
        ),
        "deerhound" to arrayOf(
            "scottish"
        ),

        "elkhound" to arrayOf(
            "norwegian"
        ),

        "finnish" to arrayOf(
            "lapphund"
        ),
        "frise" to arrayOf(
            "bichon"
        ),

        "greyhound" to arrayOf(
            "italian"
        ),

        "hound" to arrayOf(
            "afghan",
            "basset",
            "blood",
            "english",
            "ibizan",
            "plott",
            "walker"
        ),

        "mastiff" to arrayOf(
            "bull",
            "english",
            "tibetan"
        ),

        "mountain" to arrayOf(
            "bernese",
            "swiss"
        ),

        "ovcharka" to arrayOf(
            "caucasian"
        ),

        "pinscher" to arrayOf(
            "miniature"
        ),

        "pointer" to arrayOf(
            "german",
            "germanlonghair"
        ),

        "poodle" to arrayOf(
            "medium",
            "miniature",
            "standard",
            "toy"
        ),

        "retriever" to arrayOf(
            "chesapeake",
            "curly",
            "flatcoated",
            "golden"
        ),
        "ridgeback" to arrayOf(
            "rhodesian"
        ),

        "schnauzer" to arrayOf(
            "giant",
            "miniature"
        ),
        "setter" to arrayOf(
            "english",
            "gordon",
            "irish"
        ),

        "sheepdog" to arrayOf(
            "english",
            "shetland"
        ),

        "spaniel" to arrayOf(
            "blenheim",
            "brittany",
            "cocker",
            "irish",
            "japanese",
            "sussex",
            "welsh"
        ),
        "springer" to arrayOf(
            "english"
        ),

        "terrier" to arrayOf(
            "american",
            "australian",
            "bedlington",
            "border",
            "cairn",
            "dandie",
            "fox",
            "irish",
            "kerryblue",
            "lakeland",
            "norfolk",
            "norwich",
            "patterdale",
            "russell",
            "scottish",
            "sealyham",
            "silky",
            "tibetan",
            "toy",
            "welsh",
            "westhighland",
            "wheaten",
            "yorkshire"
        ),
        "waterdog" to arrayOf(
            "spanish"
        ),
        "wolfhound" to arrayOf(
            "irish"
        )
    )

    val imageUrls = arrayListOf<String>(
        "https://images.dog.ceo/breeds/chihuahua/n02085620_2921.jpg",
        "https://images.dog.ceo/breeds/saluki/n02091831_5359.jpg",
        "https://images.dog.ceo/breeds/pyrenees/n02111500_9018.jpg",
        "https://images.dog.ceo/breeds/spaniel-welsh/n02102177_368.jpg",
        "https://images.dog.ceo/breeds/terrier-wheaten/n02098105_3163.jpg",
        "https://images.dog.ceo/breeds/wolfhound-irish/n02090721_1918.jpg",
        "https://images.dog.ceo/breeds/spaniel-japanese/n02085782_3781.jpg",
        "https://images.dog.ceo/breeds/spaniel-sussex/n02102480_5021.jpg",
        "https://images.dog.ceo/breeds/cockapoo/gracie.jpg",
        "https://images.dog.ceo/breeds/shihtzu/n02086240_3493.jpg"
    )
}