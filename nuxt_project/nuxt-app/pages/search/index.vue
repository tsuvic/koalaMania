<template>
      <div>
            <NuxtLayout>

                  <Head>
                        <Title>{{ title }}</Title>
                  </Head>
                  <div class="searchResult-container">
                        <div class="p-4 container-fluid">
                              <div class="row no-gutters">
                                    <div class="col-8 pr-4">
                                          <input type="checkbox" id="isMale" value="true" v-model="isMale">
                                          <label for="isMale">オス</label>
                                          <input type="checkbox" id="isFemale" value="true" v-model="isFemale">
                                          <label for="isFemale">メス</label>
                                          <input type="checkbox" id="isAlive" value="true" v-model="isAlive">
                                          <label for="isAlive">生</label>
                                          <input type="checkbox" id="isDead" value="true" v-model="isDead">
                                          <label for="isDead">死</label>
                                    </div>
                                    <ZooListModal :isSearch="isSearch" @closeModal='closeModal' :checkedZoo="checkedZoo"/>
                                    <div class="col-4 btn btn-secondary" data-toggle="modal"
                                          data-target="#exampleModal">動物園</div>
                              </div>
                              <div class="row no-gutters">
                                    <div class="col-8  pr-4">
                                          <input class="form-control form-control form-control-borderless" type="text"
                                                name="keyword" placeholder="キーワード" id="keyword" v-model="keyword">
                                    </div>
                                    <button class="col-4 btn btn-success" type="button" @click="search">検索</button>
                              </div>
                        </div>
                        <div class="searchResult_content">
                              <div class="results">
                                    <div class="koalaList">
                                          <div class="koalaItem" v-for="animal in animalList"
                                                v-bind:key="animal.animal_id">
                                                <div class="koalaItem_header">
                                                      <div class="koalaItemName">
                                                            <img v-if="animal.sex == 1" src="/images/male.png">
                                                            <img v-else-if="animal.sex == 2" src="/images/female.png">
                                                            <img v-else src="/images/question.png">
                                                            <p>{{ animal.name }}</p>
                                                      </div>
                                                      <div class="koalaItemInfo">
                                                            <p>
                                                                  <a
                                                                        :href='`/zoo/detail/${animal.animalZooHistoryList[0].zoo.zoo_id}`'>{{
                                                                                    animal.animalZooHistoryList[0].zoo.zoo_name
                                                                        }}</a>
                                                            </p>
                                                      </div>
                                                </div>
                                                <div class="koalaItem_content">
                                                      <div class="koalaItem_image">
                                                            <a :href='`/detail/${animal.animal_id}`'>
                                                                  <img :src="animal.profileImagePath">
                                                            </a>
                                                      </div>
                                                      <div class="koalaItemData">
                                                            <div class="koalaItemDataList">

                                                                  <ul>
                                                                        <li><span
                                                                                    class="koalaItemDataList_icon birth">生年月日</span>
                                                                              <p>{{ customFormat(animal.birthdate) }}</p>
                                                                        </li>
                                                                        <li><span
                                                                                    class="koalaItemDataList_icon mother">母親</span>
                                                                              <p v-if="animal.motherAnimal !== null">
                                                                                    <a
                                                                                          :href='`/ detail / ${ animal.motherAnimal.animal_id } `'>{{
                                                                                          animal.motherAnimal.name
                                                                                          }}</a>
                                                                              </p>
                                                                              <p v-else>
                                                                                    不明
                                                                              </p>
                                                                        </li>
                                                                        <li><span
                                                                                    class="koalaItemDataList_icon father">父親</span>
                                                                              <p v-if="animal.fatherAnimal !== null">
                                                                                    <a
                                                                                          :href='`/ detail / ${ animal.fatherAnimal.animal_id } `'>{{
                                                                                          animal.fatherAnimal.name
                                                                                          }}</a>
                                                                              </p>
                                                                              <p v-else>
                                                                                    不明
                                                                              </p>
                                                                        </li>
                                                                  </ul>
                                                            </div>
                                                            <div class="links">
                                                                  <a class="btn btn-sm btn-info"
                                                                        :href='`/ detail / ${ animal.animal_id } `'>詳細情報</a>
                                                                  &nbsp;<a class="btn btn-sm btn-primary"
                                                                        :href='`/ familytree ? id = ${ animal.animal_id } `'>家系図</a>
                                                            </div>
                                                      </div>
                                                </div>
                                          </div>

                                    </div>
                              </div>
                        </div>
                  </div>
            </NuxtLayout>
      </div>
</template>

<script setup>
import axios from 'axios'

const title = ref('検索結果')
const keyword = ref('')
const isMale = ref(false)
const isFemale = ref(false)
const isAlive = ref(false)
const isDead = ref(false)
const route = useRoute();
const animalList = ref({})
const checkedZoo = ref([])
const isSearch = false
let zoo = [];

const closeModal = (zooSearch) => {
      zoo = zooSearch.value;
      checkedZoo.value = zooSearch.value;
}

const search = () => {
      animalList.value = {};
      console.log(zoo.join(','));
      axios.get('http://localhost:8080/api/animals', {
            params: {
                  // ここにクエリパラメータを指定する
                  keyword: keyword.value,
                  zoo: zoo.join(',')
            }
      }).then((response) => {
            animalList.value = response.data
      })
}

const customFormat = (customdate) => {
let date = new Date(customdate);
 return   `${date.getFullYear()}年${date.getMonth() +1 }月${date.getDate()}日`
}


checkedZoo.value = route.query.zoo;
     

onMounted(() => {
      keyword.value = route.query.keyword;
      if(route.query.zoo && route.query.zoo.includes(',')){
            zoo =  route.query.zoo.split(',');
      }else if(route.query.zoo && route.query.zoo.length > 0){
            zoo.push(route.query.zoo);
      }
      search();
})

</script>

