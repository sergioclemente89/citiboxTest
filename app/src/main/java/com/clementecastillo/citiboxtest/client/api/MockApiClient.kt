package com.clementecastillo.citiboxtest.client.api

import com.clementecastillo.citiboxtest.client.data.OrderPost
import com.clementecastillo.citiboxtest.client.data.SortPost
import com.clementecastillo.citiboxtest.domain.data.PostApp
import com.clementecastillo.citiboxtestcore.client.ApiClient
import com.clementecastillo.citiboxtestcore.domain.data.Post
import com.clementecastillo.citiboxtestcore.domain.data.PostDetails
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.Single

class MockApiClient(private val gson: Gson) : ApiClient {

    private val postJsonSample = "[\n" +
            "  {\n" +
            "    \"userId\": 6,\n" +
            "    \"id\": 58,\n" +
            "    \"title\": \"voluptatum itaque dolores nisi et quasi\",\n" +
            "    \"body\": \"veniam voluptatum quae adipisci id\\net id quia eos ad et dolorem\\naliquam quo nisi sunt eos impedit error\\nad similique veniam\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"userId\": 7,\n" +
            "    \"id\": 70,\n" +
            "    \"title\": \"voluptatem laborum magni\",\n" +
            "    \"body\": \"sunt repellendus quae\\nest asperiores aut deleniti esse accusamus repellendus quia aut\\nquia dolorem unde\\neum tempora esse dolore\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"userId\": 2,\n" +
            "    \"id\": 14,\n" +
            "    \"title\": \"voluptatem eligendi optio\",\n" +
            "    \"body\": \"fuga et accusamus dolorum perferendis illo voluptas\\nnon doloremque neque facere\\nad qui dolorum molestiae beatae\\nsed aut voluptas totam sit illum\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"userId\": 7,\n" +
            "    \"id\": 61,\n" +
            "    \"title\": \"voluptatem doloribus consectetur est ut ducimus\",\n" +
            "    \"body\": \"ab nemo optio odio\\ndelectus tenetur corporis similique nobis repellendus rerum omnis facilis\\nvero blanditiis debitis in nesciunt doloribus dicta dolores\\nmagnam minus velit\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"userId\": 2,\n" +
            "    \"id\": 18,\n" +
            "    \"title\": \"voluptate et itaque vero tempora molestiae\",\n" +
            "    \"body\": \"eveniet quo quis\\nlaborum totam consequatur non dolor\\nut et est repudiandae\\nest voluptatem vel debitis et magnam\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"userId\": 7,\n" +
            "    \"id\": 63,\n" +
            "    \"title\": \"voluptas blanditiis repellendus animi ducimus error sapiente et suscipit\",\n" +
            "    \"body\": \"enim adipisci aspernatur nemo\\nnumquam omnis facere dolorem dolor ex quis temporibus incidunt\\nab delectus culpa quo reprehenderit blanditiis asperiores\\naccusantium ut quam in voluptatibus voluptas ipsam dicta\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"userId\": 5,\n" +
            "    \"id\": 48,\n" +
            "    \"title\": \"ut voluptatem illum ea doloribus itaque eos\",\n" +
            "    \"body\": \"voluptates quo voluptatem facilis iure occaecati\\nvel assumenda rerum officia et\\nillum perspiciatis ab deleniti\\nlaudantium repellat ad ut et autem reprehenderit\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"userId\": 6,\n" +
            "    \"id\": 53,\n" +
            "    \"title\": \"ut quo aut ducimus alias\",\n" +
            "    \"body\": \"minima harum praesentium eum rerum illo dolore\\nquasi exercitationem rerum nam\\nporro quis neque quo\\nconsequatur minus dolor quidem veritatis sunt non explicabo similique\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"userId\": 5,\n" +
            "    \"id\": 45,\n" +
            "    \"title\": \"ut numquam possimus omnis eius suscipit laudantium iure\",\n" +
            "    \"body\": \"est natus reiciendis nihil possimus aut provident\\nex et dolor\\nrepellat pariatur est\\nnobis rerum repellendus dolorem autem\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"userId\": 4,\n" +
            "    \"id\": 31,\n" +
            "    \"title\": \"ullam ut quidem id aut vel consequuntur\",\n" +
            "    \"body\": \"debitis eius sed quibusdam non quis consectetur vitae\\nimpedit ut qui consequatur sed aut in\\nquidem sit nostrum et maiores adipisci atque\\nquaerat voluptatem adipisci repudiandae\"\n" +
            "  }" +
            "]"

    private fun postList(): List<Post> {
        return gson.fromJson(postJsonSample, object : TypeToken<ArrayList<PostApp>>() {}.type)
    }

    override fun getPosts(currentItemCount: Int, sortField: String, order: String): Single<List<Post>> {
        val list = when (sortField) {
            SortPost.ID.fieldName -> {
                if (order == OrderPost.ASC.fieldName) {
                    postList().sortedBy { it.id }
                } else {
                    postList().sortedByDescending { it.id }
                }
            }
            SortPost.USER.fieldName -> {
                if (order == OrderPost.ASC.fieldName) {
                    postList().sortedBy { it.userId }
                } else {
                    postList().sortedByDescending { it.userId }
                }
            }
            SortPost.TITLE.fieldName -> {
                if (order == OrderPost.ASC.fieldName) {
                    postList().sortedBy { it.title }
                } else {
                    postList().sortedByDescending { it.title }
                }
            }
            SortPost.BODY.fieldName -> {
                if (order == OrderPost.ASC.fieldName) {
                    postList().sortedBy { it.body }
                } else {
                    postList().sortedByDescending { it.body }
                }
            }
            else -> throw IllegalArgumentException()
        }

        return Single.just(list)
    }

    override fun getPost(postId: Int): Single<Post> {
        return postList().find { it.id == postId }.let {
            if (it == null) {
                throw IllegalArgumentException()
            } else {
                Single.just(it)
            }
        }
    }

    override fun getPostDetails(post: Post): Single<PostDetails> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}