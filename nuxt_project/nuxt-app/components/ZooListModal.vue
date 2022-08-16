<template>
    <!-- Modal 動物園-->
    <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">動物園で絞り込み</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>

                <div class="modal-body">
                    <div v-for="zoo in zooList" v-bind:key=zoo.zoo_id>
                        <input type="checkbox" :value="zoo.zoo_id" v-model="checkedZoo" :id='`zooId_${zoo.zoo_id}`'>
                        <label :for='`zooId_${zoo.zoo_id}`'>{{ zoo.zoo_name }}</label>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-primary" data-dismiss="modal" @click="parentFunc">{{buttonMessage}}</button>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import axios from 'axios'

const zooList = ref([])
const buttonMessage = ref("")
const checkedZoo = ref([])

axios.get('http://localhost:8080/api/zoo').then((response) => {
    zooList.value = response.data
})

const props = defineProps({
    isSearch: Boolean,
    checkedZoo: String
})

const emit = defineEmits(['search','closeModal'])

const parentFunc = () => {
    if (props.isSearch) {
        emit('searchFromZoo', checkedZoo)
    } else {
        emit('closeModal', checkedZoo)
    }
}
if(props.checkedZoo && props.checkedZoo.includes(',')){

    props.checkedZoo.split(',').forEach((element) => {
          checkedZoo.value.push(element);
    })

}else if(props.checkedZoo && props.checkedZoo.length > 0){
    checkedZoo.value.push(props.checkedZoo);
}

onMounted(() => {
      if (props.isSearch) {
        buttonMessage.value = "さがす"
    } else {
        buttonMessage.value = "閉じる"
    }
})

</script>